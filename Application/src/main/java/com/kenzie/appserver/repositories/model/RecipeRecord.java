package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "RecipeTable")
public class RecipeRecord {
    private String id;
    private String name;
    private String foodType;
    private String ingredients;
    private String timeToPrepare;
    private String instructions;
    private int favoriteCount;

    @DynamoDBHashKey(attributeName = "id")
    public String getId(){
        return id;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName(){
        return name;
    }
    @DynamoDBAttribute(attributeName = "foodType")
    public String getFoodType() {
        return foodType;
    }
    @DynamoDBAttribute(attributeName = "ingredients")
    public String getIngredients() {
        return ingredients;
    }
    @DynamoDBAttribute(attributeName = "timeToPrepare")
    public String getTimeToPrepare() {
        return timeToPrepare;
    }
    @DynamoDBAttribute(attributeName = "instructions")
    public String getInstructions() {
        return instructions;
    }
    @DynamoDBAttribute(attributeName = "favoriteCount")
    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
    public void setTimeToPrepare(String timeToPrepare) {
        this.timeToPrepare = timeToPrepare;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeRecord that = (RecipeRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
