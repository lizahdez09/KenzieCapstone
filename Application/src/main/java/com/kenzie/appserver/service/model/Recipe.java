package com.kenzie.appserver.service.model;

import com.kenzie.appserver.repositories.model.RecipeRecord;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String id;
    private String name;

    private FoodType foodType;
    private List<Ingredient> ingredients;
    private String timeToPrepare;

    public Recipe() {
        /*DO NOT REMOVE*/
    }

    public Recipe(String id, String name, FoodType foodType, List<Ingredient> ingredients, String timeToPrepare){
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.ingredients = ingredients;
        this.timeToPrepare = timeToPrepare;
    }

    public Recipe(RecipeRecord record){
        this.id = record.getId();
        this.foodType = FoodType.fromValue(record.getFoodType());
        this.name = record.getName();
        this.ingredients = IngredientConverter.jsonToIngredients(record.getIngredients());
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

    public FoodType getFoodType() {
        return foodType;
    }

    public String getFoodTypeAsString() {
        return this.foodType.getValue();
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public String getIngredientsAsString() {
        return IngredientConverter.ingredientsToJson(this.ingredients);
    }

    public List<Ingredient> getIngredientsAsList() {
        return this.ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = IngredientConverter.jsonToIngredients(ingredients);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getTimeToPrepare() {
        return timeToPrepare;
    }

    public void setTimeToPrepare(String timeToPrepare) {
        this.timeToPrepare = timeToPrepare;
    }

}
