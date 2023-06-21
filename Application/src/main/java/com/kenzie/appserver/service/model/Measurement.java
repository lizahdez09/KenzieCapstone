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

    public static Measurement fromString(String value) {
        for (Measurement measurement : Measurement.values()) {
            if (measurement.stringValue.equalsIgnoreCase(value)) {
                return measurement;
            }
        }
        throw new IllegalArgumentException("Invalid measurement value: " + value);
    }
}
