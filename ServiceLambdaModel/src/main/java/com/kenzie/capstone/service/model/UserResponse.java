package com.kenzie.capstone.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;
    @JsonProperty("password")
    private String password;
    @JsonProperty("favoriteRecipes")
    private String favoriteRecipes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        return "UserResponse{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", favoriteRecipes='" + favoriteRecipes + '\'' +
                '}';
    }
}
