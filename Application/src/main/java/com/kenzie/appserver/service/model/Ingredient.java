package com.kenzie.appserver.service.model;

public class Ingredient {

    private String name;
    private String amount;
    private Measurement measurement;

    public Ingredient() {

    }
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

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", amount='" + amount + '\'' +
                ", measurement='" + measurement.getStringValue() + '\'' +
                '}';
    }
}
