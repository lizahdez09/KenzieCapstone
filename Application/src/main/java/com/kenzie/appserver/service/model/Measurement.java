package com.kenzie.appserver.service.model;

import com.kenzie.appserver.exceptions.InvalidMeasurementException;

public enum Measurement {
    TEASPOON("teaspoon"),
    TABLESPOON("tablespoon"),
    CUP("cup"),
    COUNT("count"),
    POUND("pound");


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
        throw new InvalidMeasurementException("Invalid measurement value: " + value);
    }
}
