package com.kenzie.capstone.service.model;

public class UserUpdateRequest {
    private String email;
    private String favoriteRecipes;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(String favoriteRecipes) {
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
