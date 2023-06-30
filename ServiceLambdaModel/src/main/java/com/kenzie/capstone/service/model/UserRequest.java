package com.kenzie.capstone.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {
    private String email;
    private String name;
    private String password;
    private String favoriteRecipes;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(String favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", favoriteRecipes='" + favoriteRecipes + '\'' +
                '}';
    }
}
