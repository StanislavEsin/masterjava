package ru.javaops.masterjava;

import ru.javaops.masterjava.xml.schema_hw.*;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import com.google.common.io.Resources;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import javax.xml.bind.JAXBException;

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

        List<User> usersJAXB = new MainXml().getSortedListUsersByNameProjectUsingJAXB(args[0], args[1]);
        List<User> usersStAX = new MainXml().getSortedListUsersByNameProjectUsingStAX(args[0], args[1]);
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
}