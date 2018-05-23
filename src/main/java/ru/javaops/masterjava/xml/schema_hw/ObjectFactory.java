package ru.javaops.masterjava.xml.schema_hw;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
    private final static QName _City_QNAME = new QName("http://javaops.ru", "City");

    public ObjectFactory() {
    }

    public Project createProject() {
        return new Project();
    }

    public Payload createPayload() {
        return new Payload();
    }

    public Project.Groups createProjectGroups() {
        return new Project.Groups();
    }

    public User createUser() {
        return new User();
    }

    public Payload.Cities createPayloadCities() {
        return new Payload.Cities();
    }

    public Payload.Projects createPayloadProjects() {
        return new Payload.Projects();
    }

    public Payload.Users createPayloadUsers() {
        return new Payload.Users();
    }

    public CityType createCityType() {
        return new CityType();
    }

    public Project.Groups.Group createProjectGroupsGroup() {
        return new Project.Groups.Group();
    }

    @XmlElementDecl(namespace = "http://javaops.ru", name = "City")
    public JAXBElement<CityType> createCity(CityType value) {
        return new JAXBElement<CityType>(_City_QNAME, CityType.class, null, value);
    }
}