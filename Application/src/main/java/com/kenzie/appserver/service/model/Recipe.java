package com.kenzie.appserver.service.model;

import com.kenzie.appserver.repositories.model.RecipeRecord;

import java.util.List;

public class Recipe {
    private String id;
    private String name;
    private FoodType foodType;
    private List<RecipeIngredient> recipeIngredients;
    private String timeToPrepare;
    private String instructions;
    private int favoriteCount;

    public Recipe() {
        /*DO NOT REMOVE - Need for JSON processing*/
    }

    public Recipe(String id, String name, FoodType foodType, List<RecipeIngredient> recipeIngredients,
                  String timeToPrepare, String instructions, int favoriteCount){
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.recipeIngredients = recipeIngredients;
        this.timeToPrepare = timeToPrepare;
        this.instructions = instructions;
        this.favoriteCount = favoriteCount;
    }

    public Recipe(RecipeRecord record){
        this.id = record.getId();
        this.foodType = FoodType.fromValue(record.getFoodType());
        this.name = record.getName();
        this.recipeIngredients = RecipeIngredientConverter.jsonToIngredients(record.getIngredients());
        this.timeToPrepare = record.getTimeToPrepare();
        this.instructions = record.getInstructions();
        this.favoriteCount = record.getFavoriteCount();
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
        return RecipeIngredientConverter.ingredientsToJson(this.recipeIngredients);
    }

    public List<RecipeIngredient> getIngredientsAsList() {
        return this.recipeIngredients;
    }

    public void setIngredients(String ingredients) {
        this.recipeIngredients = RecipeIngredientConverter.jsonToIngredients(ingredients);
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void increaseFavoriteCount() {
        this.favoriteCount ++;
    }

}
