package ru.javaops.masterjava.dto;

public enum Flag {
    ACTIVE("active"),
    SUPERUSER("superuser"),
    DELETE("deleted"),
    EMPTY("empty");

    String description;

    Flag(String description) {
        this.description = description;
    }

    public static Flag getByDescription(String description) {
        Flag result = EMPTY;

        for (Flag flag : Flag.values()) {
            if (description.equals(flag.description)) {
                result = flag;
                break;
            }
        }

        return result;
    }
}