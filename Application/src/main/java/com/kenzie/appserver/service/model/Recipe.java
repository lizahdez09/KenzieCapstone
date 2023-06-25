package com.kenzie.appserver.service.model;

import com.kenzie.appserver.repositories.model.RecipeRecord;

import java.util.List;

public class Recipe {
    private String id;
    private String name;

    private FoodType foodType;
    private List<RecipeIngredient> recipeIngredients;
    private String timeToPrepare;

    public Recipe() {
        /*DO NOT REMOVE - Need for JSON processing*/
    }

    public Recipe(String id, String name, FoodType foodType, List<RecipeIngredient> recipeIngredients, String timeToPrepare){
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.recipeIngredients = recipeIngredients;
        this.timeToPrepare = timeToPrepare;
    }

    public Recipe(RecipeRecord record){
        this.id = record.getId();
        this.foodType = FoodType.fromValue(record.getFoodType());
        this.name = record.getName();
        this.recipeIngredients = IngredientConverter.jsonToIngredients(record.getIngredients());
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
        return IngredientConverter.ingredientsToJson(this.recipeIngredients);
    }

    public List<RecipeIngredient> getIngredientsAsList() {
        return this.recipeIngredients;
    }

    public void setIngredients(String ingredients) {
        this.recipeIngredients = IngredientConverter.jsonToIngredients(ingredients);
    }

    public void setIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getTimeToPrepare() {
        return timeToPrepare;
    }

    public void setTimeToPrepare(String timeToPrepare) {
        this.timeToPrepare = timeToPrepare;
    }

}
