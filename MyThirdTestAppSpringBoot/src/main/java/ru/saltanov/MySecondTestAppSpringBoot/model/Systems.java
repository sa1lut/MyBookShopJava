package ru.saltanov.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Systems {
    EMPTY(""),
    ERP("ERP"),
    CRM("CRM"),
    WMS("WMS"),
    SERVICE1("Service 1");

    private final String description;

    Systems(String description) {
        this.description = description;
    }

    @JsonValue
    public String getName() {
        return description;
    }
}
