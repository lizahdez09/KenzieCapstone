package com.kenzie.appserver.service.model;

public class RecipeIngredient extends Ingredient {

    private String id;
    private String name;
    private String amount;
    private Measurement measurement;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setMeasurement(String measurement) {
        this.measurement = Measurement.fromString(measurement);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", amount='" + amount + '\'' +
                ", measurement='" + measurement.getStringValue() + '\'' +
                '}';
    }
}
