package com.kenzie.capstone.service.model;

import java.util.Objects;

public class UserData {

    private String userId;
    private String recipeId;
    private String name;

    public UserData(String userId, String recipeId, String name) {
        this.userId = userId;
        this.recipeId = recipeId;
        this.name = name;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(userId, userData.userId) &&
                Objects.equals(recipeId, userData.recipeId) &&
                Objects.equals(name, userData.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, recipeId, name);
    }
}
