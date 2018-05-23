package ru.javaops.masterjava.xml.schema_hw;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"value"})
@XmlRootElement(name = "User", namespace = "http://javaops.ru")
public class User {
    @XmlValue
    protected String value;

    @XmlAttribute(name = "email", required = true)
    protected String email;

    @XmlAttribute(name = "flag", required = true)
    protected FlagType flag;

    @XmlAttribute(name = "city", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object city;

    @XmlAttribute(name = "groupRefs")
    @XmlIDREF
    @XmlSchemaType(name = "IDREFS")
    protected List<Project.Groups.Group> groupRefs;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public FlagType getFlag() {
        return flag;
    }

    public void setFlag(FlagType value) {
        this.flag = value;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object value) {
        this.city = value;
    }

    public List<Project.Groups.Group> getGroupRefs() {
        if (groupRefs == null) {
            groupRefs = new ArrayList<Project.Groups.Group>();
        }
        return this.groupRefs;
    }
}