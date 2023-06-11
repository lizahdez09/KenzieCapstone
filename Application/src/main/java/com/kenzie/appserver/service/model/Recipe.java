package com.kenzie.appserver.service.model;

import com.kenzie.appserver.repositories.model.RecipeRecord;

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

    public Recipe(RecipeRecord record){
        this.id = record.getRecipeId();
        this.name = record.getRecipeName();
        this.ingredients = record.getIngredients();
        this.timeToPrepare = record.getTimeToPrepare();
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
