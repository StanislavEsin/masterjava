package ru.javaops.masterjava.xml.schema_hw;

import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"description", "groups"})
@XmlRootElement(name = "Project", namespace = "http://javaops.ru")
public class Project {
    @XmlElement(namespace = "http://javaops.ru", required = true)
    protected String description;

    @XmlElement(name = "Groups", namespace = "http://javaops.ru", required = true)
    protected Project.Groups groups;

    @XmlAttribute(name = "name", required = true)
    protected String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public Project.Groups getGroups() {
        return groups;
    }

    public void setGroups(Project.Groups value) {
        this.groups = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"group"})
    public static class Groups {
        @XmlElement(name = "Group", namespace = "http://javaops.ru", required = true)
        protected List<Project.Groups.Group> group;

        public List<Project.Groups.Group> getGroup() {
            if (group == null) {
                group = new ArrayList<Project.Groups.Group>();
            }
            return this.group;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Group {
            @XmlAttribute(name = "name", required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlID
            @XmlSchemaType(name = "ID")
            protected String name;

            @XmlAttribute(name = "type", required = true)
            protected GroupType type;

            public String getName() {
                return name;
            }

            public void setName(String value) {
                this.name = value;
            }

            public GroupType getType() {
                return type;
            }

            public void setType(GroupType value) {
                this.type = value;
            }
        }
    }
}