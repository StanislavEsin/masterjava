package ru.javaops.masterjava;

import ru.javaops.masterjava.xml.schema_hw.*;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.function.BiPredicate;
import java.util.Optional;
import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;
import javax.xml.bind.JAXBException;
import java.io.IOException;

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

        List<User> users = new MainXml().getSortedListUsersByNameProjectUsingJAXB(args[0], args[1]);

    }

    public List<User> getSortedListUsersByNameProjectUsingJAXB(String projectName, String xmlName) {
        List<User> result = new ArrayList<>();

        JaxbParser jaxbParser = new JaxbParser(ObjectFactory.class);
        jaxbParser.setSchema(Schemas.ofClasspath("payload.xsd"));

        BiPredicate<List<Project.Groups.Group>,
                List<Project.Groups.Group>> isIncludedInProject = (projectGroups, userGroups) -> {
            for (Project.Groups.Group group : userGroups) {
                if (projectGroups.contains(group)) return true;
            }

            return false;
        };

        try {
            Payload payload = jaxbParser.unmarshal(Resources.getResource(xmlName).openStream());
            Optional<Project> project = payload.getProjects().getProject().stream()
                    .filter(v -> projectName.equals(v.getName())).findFirst();

            if (project.isPresent()) {
                List<Project.Groups.Group> projectGroups = project.get().getGroups().getGroup();

                result = payload.getUsers().getUser().stream()
                        .filter(user -> isIncludedInProject.test(projectGroups, user.getGroupRefs()))
                        .sorted(Comparator.comparing(User::getValue))
                        .collect(Collectors.toList());
            }
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}