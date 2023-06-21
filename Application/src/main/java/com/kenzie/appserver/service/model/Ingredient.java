package com.kenzie.appserver.service.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ingredient {

    private String name;
    private String amount;
    private Measurement measurement;

    public Ingredient(String name, String amount, Measurement measurement) {
        this.name = name;
        this.amount = amount;
        this.measurement = measurement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public static Ingredient fromString(String ingredientString) {
        Matcher matcher = Pattern.compile("name='(.*?)', amount='(.*?)', measurement='(.*?)'}")
                .matcher(ingredientString);

        if (matcher.find()) {
            String name = matcher.group(1);
            String amount = matcher.group(2);
            String measurementValue = matcher.group(3);
            Measurement measurement = null;

            for (Measurement meas : Measurement.values()) {
                if (meas.getStringValue().equals(measurementValue)) {
                    measurement = meas;
                    break;
                }
            }
            if (measurement != null) {
                return new Ingredient(name, amount, measurement);
            } else {
                throw new IllegalArgumentException("Invalid measurement value: " + measurementValue);
            }
        } else {
            throw new IllegalArgumentException("Invalid ingredient string format: " + ingredientString);
        }
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", amount='" + amount + '\'' +
                ", measurement=" + measurement +
                '}';
    }
}
