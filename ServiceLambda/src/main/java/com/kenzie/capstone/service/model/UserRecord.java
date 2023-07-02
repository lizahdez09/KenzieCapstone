package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "UserTable")
public class UserRecord {

    private String id;
    private String email;
    private String name;
    private String password;
    private String favoriteRecipes;


    @DynamoDBAttribute(attributeName = "id")
    public String getId() {
        return id;
    }
    @DynamoDBHashKey(attributeName = "email")
    public String getEmail() {
        return email;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }
    @DynamoDBAttribute(attributeName = "password")
    public String getPassword() {
        return password;
    }
    @DynamoDBAttribute(attributeName = "favoriteRecipes")
    public String getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFavoriteRecipes(String favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRecord that = (UserRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(favoriteRecipes, that.favoriteRecipes) && Objects.equals(password, that.password) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, favoriteRecipes, password, email);
    }
}
