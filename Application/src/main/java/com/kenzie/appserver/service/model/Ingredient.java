package com.kenzie.appserver.service.model;

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

    public String toCSVString(){
        return this.name + "," + this.amount + "," + this.measurement.getStringValue();
    }

    public static Ingredient fromCSVString(String csvString) {
        String[] fields = csvString.split(",");
        return new Ingredient(fields[0], fields[1], Measurement.valueOf(fields[2]));
    }
}
