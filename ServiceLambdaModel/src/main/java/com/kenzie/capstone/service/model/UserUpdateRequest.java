package com.kenzie.capstone.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserUpdateRequest {
    @JsonProperty("email")
    private String email;
    @JsonProperty("favoriteRecipes")
    private List<String> favoriteRecipes;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(List<String> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    @Override
    public String toString() {
        return "UserUpdateRequest{" +
                "email='" + email + '\'' +
                ", favoriteRecipes='" + favoriteRecipes + '\'' +
                '}';
    }
}
