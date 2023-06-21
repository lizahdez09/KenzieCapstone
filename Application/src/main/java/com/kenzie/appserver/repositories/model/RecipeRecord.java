package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "RecipeTable")
public class RecipeRecord {
    private String id;
    private String name;
    private String ingredients;
    private String timeToPrepare;

    @DynamoDBHashKey(attributeName = "id")
    public String getId(){
        return id;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName(){
        return name;
    }
    @DynamoDBAttribute(attributeName = "ingredients")
    public String getIngredients() {
        return ingredients;
    }
    @DynamoDBAttribute(attributeName = "timeToPrepare")
    public String getTimeToPrepare() {
        return timeToPrepare;
    }

    public void setRecipeId(String id) {
        this.id = id;
    }
    public void setRecipeName(String name) {
        this.name = name;
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
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
