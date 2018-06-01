package ru.javaops.masterjava.xml.util;

import java.io.*;
import org.xml.sax.SAXException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;

/**
 * Marshalling/Unmarshalling JAXB helper
 * XML Facade
 */
public class JaxbParser {
    private JAXBContext ctx;
    private Schema schema;

    public JaxbParser(Class... classesToBeBound) {
        try {
            ctx = JAXBContext.newInstance(classesToBeBound);
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public JaxbParser(String context) {
        try {
            ctx = JAXBContext.newInstance(context);
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public void validate(String str) throws IOException, SAXException {
        validate(new StringReader(str));
    }

    public void validate(Reader reader) throws IOException, SAXException {
        schema.newValidator().validate(new StreamSource(reader));
    }

    public JaxbMarshaller createMarshaller() {
        try {
            JaxbMarshaller marshaller = new JaxbMarshaller(ctx);
            if (schema != null) marshaller.setSchema(schema);

            return marshaller;
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    public JaxbUnmarshaller createUnmarshaller() {
        try {
            JaxbUnmarshaller unmarshaller = new JaxbUnmarshaller(ctx);
            if (schema != null) unmarshaller.setSchema(schema);

            return unmarshaller;
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}