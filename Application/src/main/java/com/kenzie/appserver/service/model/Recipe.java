package com.kenzie.appserver.service.model;

import com.kenzie.appserver.repositories.model.RecipeRecord;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String id;
    private String name;
    private List<Ingredient> ingredients;
    private String timeToPrepare;

    public Recipe(String id, String name, List<Ingredient> ingredients, String timeToPrepare){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.timeToPrepare = timeToPrepare;
    }

    public Recipe(RecipeRecord record){
        this.id = record.getId();
        this.name = record.getName();
        this.ingredients = convertStringToIngredientList(record.getIngredients());
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

    //TODO FIX THIS LOL
    public String getIngredientsAsString() {
        if (this.ingredients.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();

        for (Ingredient ingredient : this.ingredients) {
            sb.append(ingredient.toString()).append("; ");
        }

        sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    public List<Ingredient> getIngredientsAsList() {
        return this.ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = convertStringToIngredientList(ingredients);
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

    private List<Ingredient> convertStringToIngredientList(String ingredientsString) {
        List<Ingredient> ingredientList = new ArrayList<>();

        if (ingredientsString != null && !ingredientsString.isEmpty()) {
            String[] ingredientArray = ingredientsString.split("}");

            for (String ingredientFromArray : ingredientArray) {
                Ingredient ingredient = Ingredient.fromString(ingredientFromArray);
                ingredientList.add(ingredient);
            }
        }

        return ingredientList;
    }

}
