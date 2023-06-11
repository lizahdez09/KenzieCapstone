package com.kenzie.appserver.service.model;

public class Recipe {
    private String id;
    private String name;
    private String ingredients;
    private String timeToPrepare;

    public Recipe(String id, String name, String ingredients, String timeToPrepare){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.timeToPrepare = timeToPrepare;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getTimeToPrepare() {
        return timeToPrepare;
    }

    public void setTimeToPrepare(String timeToPrepare) {
        this.timeToPrepare = timeToPrepare;
    }
}
