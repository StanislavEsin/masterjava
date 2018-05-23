package ru.javaops.masterjava.xml.schema_hw;

import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"cities", "projects", "users"})
@XmlRootElement(name = "Payload", namespace = "http://javaops.ru")
public class Payload {
    @XmlElement(name = "Cities", namespace = "http://javaops.ru", required = true)
    protected Payload.Cities cities;

    @XmlElement(name = "Projects", namespace = "http://javaops.ru", required = true)
    protected Payload.Projects projects;

    @XmlElement(name = "Users", namespace = "http://javaops.ru", required = true)
    protected Payload.Users users;

    public Payload.Cities getCities() {
        return cities;
    }

    public void setCities(Payload.Cities value) {
        this.cities = value;
    }

    public Payload.Projects getProjects() {
        return projects;
    }

    public void setProjects(Payload.Projects value) {
        this.projects = value;
    }

    public Payload.Users getUsers() {
        return users;
    }

    public void setUsers(Payload.Users value) {
        this.users = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"city"})
    public static class Cities {
        @XmlElement(name = "City", namespace = "http://javaops.ru", required = true)
        protected List<CityType> city;

        public List<CityType> getCity() {
            if (city == null) {
                city = new ArrayList<CityType>();
            }

            return this.city;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"project"})
    public static class Projects {
        @XmlElement(name = "Project", namespace = "http://javaops.ru")
        protected List<Project> project;

        public List<Project> getProject() {
            if (project == null) {
                project = new ArrayList<Project>();
            }

            return this.project;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"user"})
    public static class Users {
        @XmlElement(name = "User", namespace = "http://javaops.ru")
        protected List<User> user;

        public List<User> getUser() {
            if (user == null) {
                user = new ArrayList<User>();
            }

            return this.user;
        }
    }
}