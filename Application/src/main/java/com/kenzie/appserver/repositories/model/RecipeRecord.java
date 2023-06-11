package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "RecipeTable")
public class RecipeRecord {
    private String recipeId;
    private String recipeName;
    private String ingredients;
    private String timeToPrepare;

    @DynamoDBHashKey(attributeName = "recipeId")
    public String getRecipeId(){
        return recipeId;
    }
    @DynamoDBAttribute(attributeName = "recipeName")
    public String getRecipeName(){
        return recipeName;
    }
    @DynamoDBAttribute(attributeName = "ingredients")
    public String getIngredients() {
        return ingredients;
    }
    @DynamoDBAttribute(attributeName = "timeToPrepare")
    public String getTimeToPrepare() {
        return timeToPrepare;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
    public void setTimeToPrepare(String timeToPrepare) {
        this.timeToPrepare = timeToPrepare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeRecord that = (RecipeRecord) o;
        return Objects.equals(recipeId, that.recipeId) && Objects.equals(recipeName, that.recipeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, recipeName);
    }
}
