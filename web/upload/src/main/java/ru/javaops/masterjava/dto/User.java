package ru.javaops.masterjava.dto;

import java.util.Objects;

/**
 * User.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 30.05.2018
 */
public class User {
    String fullName;
    String email;
    Flag flag;

    public User() {
    }

    public User(String fullName, String email, Flag flag) {
        this.fullName = fullName;
        this.email = email;
        this.flag = flag;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", flag=" + flag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(fullName, user.fullName) &&
                Objects.equals(email, user.email) &&
                flag == user.flag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, email, flag);
    }
}