package ru.javaops.masterjava.xml.util;

import ru.javaops.masterjava.dto.User;
import ru.javaops.masterjava.dto.Flag;
import java.util.Set;
import java.util.HashSet;
import java.net.URL;
import java.io.InputStream;
import javax.xml.stream.events.XMLEvent;
import java.util.stream.Stream;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;

/**
 * DTOUtils.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 31.05.2018
 */
public class DTOUtils {
    public Stream<User> usersFromXMLFile(String file) throws IOException, XMLStreamException {
        Set<User> result = new HashSet<>();

        URL url = new URL("file:/" + file);
        try (InputStream is = url.openStream()) {
            StaxStreamProcessor processor = new StaxStreamProcessor(is);
            while (processor.doUntil(XMLEvent.START_ELEMENT, "User")) {
                User user = new User();
                user.setEmail(processor.getAttribute("email"));
                user.setFlag(Flag.getByDescription(processor.getAttribute("flag")));
                user.setFullName(processor.getText());
                result.add(user);
            }
        } catch (XMLStreamException e) {
            throw e;
        }

        return result.stream();
    }
}