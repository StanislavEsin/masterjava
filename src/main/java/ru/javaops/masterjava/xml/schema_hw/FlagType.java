package ru.javaops.masterjava.xml.schema_hw;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "flagType", namespace = "http://javaops.ru")
@XmlEnum
public enum FlagType {
    @XmlEnumValue("active")
    ACTIVE("active"),

    @XmlEnumValue("deleted")
    DELETED("deleted"),

    @XmlEnumValue("superuser")
    SUPERUSER("superuser");
    private final String value;

    FlagType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FlagType fromValue(String v) {
        for (FlagType c: FlagType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}