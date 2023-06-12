package com.kenzie.appserver.service.model;

public enum Measurement {
    TEASPOON("tsp"),
    TABLESPOON("tbsp"),
    CUP("c");


    private final String stringValue;

    Measurement(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
