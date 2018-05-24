package ru.javaops.masterjava;

import ru.javaops.masterjava.xml.schema_hw.*;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;
import ru.javaops.masterjava.xml.util.XsltProcessor;
import java.util.*;
import java.util.stream.Collectors;
import com.google.common.io.Resources;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

/**
 * MainXml.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 23.05.2018
 */
public class MainXml {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Format: projectName, xmlName");
            System.exit(1);
        }

        MainXml mainXml = new MainXml();

        List<User> usersJAXB = mainXml.getSortedListUsersByNameProjectUsingJAXB(args[0], args[1]);
        List<User> usersStAX = mainXml.getSortedListUsersByNameProjectUsingStAX(args[0], args[1]);

        String members = mainXml.getTransformedXml(args[1], "list_of_members.xsl", null).trim();

        Map<String, String> params = new HashMap<>();
        params.put("projectName", args[0]);
        String groupsByProject = mainXml.getTransformedXml(args[1], "with_groups_by_project.xsl", params).trim();
    }

    public List<User> getSortedListUsersByNameProjectUsingJAXB(String projectName, String xmlName) {
        List<User> result = new ArrayList<>();

        JaxbParser jaxbParser = new JaxbParser(ObjectFactory.class);
        jaxbParser.setSchema(Schemas.ofClasspath("payload.xsd"));

        try {
            Payload payload = jaxbParser.unmarshal(Resources.getResource(xmlName).openStream());
            Optional<Project> project = payload.getProjects().getProject().stream()
                    .filter(v -> projectName.equals(v.getName())).findFirst();

            if (project.isPresent()) {
                List<Project.Groups.Group> projectGroups = project.get().getGroups().getGroup();

                result = payload.getUsers().getUser().stream()
                        .filter(user -> user.getGroupRefs().stream().anyMatch(projectGroups::contains))
                        .sorted(Comparator.comparing(User::getValue))
                        .collect(Collectors.toList());
            }
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<User> getSortedListUsersByNameProjectUsingStAX(String projectName, String xmlName) {
        List<User> result = new ArrayList<>();

        try (StaxStreamProcessor processor = new StaxStreamProcessor(Resources.getResource(xmlName).openStream())) {
            XMLStreamReader reader = processor.getReader();

            List<String> projectGroups = new ArrayList<>();

            boolean pointerProject = false;
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLEvent.START_ELEMENT) {
                    if ("Project".equals(reader.getLocalName())) {
                        pointerProject = projectName.equals(reader.getAttributeValue(0));
                    }

                    if ("Group".equals(reader.getLocalName()) && pointerProject) {
                        projectGroups.add(reader.getAttributeValue(0));
                    }

                    if ("User".equals(reader.getLocalName())) {
                        List<String> userGroups = Arrays.asList(reader.getAttributeValue(3).split(" "));

                        if (userGroups.stream().anyMatch(projectGroups::contains)) {
                            User user = new User();
                            user.setEmail(reader.getAttributeValue(0));
                            user.setValue(reader.getElementText());
                            result.add(user);
                        }
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }

        return result.stream()
                .sorted(Comparator.comparing(User::getValue))
                .collect(Collectors.toList());
    }

    public String getTransformedXml(String xmlName, String xslName, Map<String, String> parameters) {
        String result = "";

        try (InputStream xslInputStream = Resources.getResource(xslName).openStream();
             InputStream xmlInputStream = Resources.getResource(xmlName).openStream()) {
            XsltProcessor processor = new XsltProcessor(xslInputStream);

            if (parameters != null) parameters.forEach(processor::setParameter);

            result = processor.transform(xmlInputStream);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }

        return result;
    }
}