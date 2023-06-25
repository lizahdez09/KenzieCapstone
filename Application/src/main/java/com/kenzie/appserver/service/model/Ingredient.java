package com.kenzie.appserver.service.model;

import com.kenzie.appserver.repositories.model.IngredientRecord;

public class Ingredient {

    private String id;
    private String name;

    public Ingredient() {

    }
    public Ingredient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ingredient(IngredientRecord record) {
        this.id = record.getId();
        this.name = record.getName();
    }

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

    @Override
    public String toString() {
        return "Ingredient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
