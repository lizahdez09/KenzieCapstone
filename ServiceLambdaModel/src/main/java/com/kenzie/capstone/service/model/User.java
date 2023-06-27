package com.kenzie.capstone.service.model;

import java.util.List;
import java.util.Objects;

public class User {

    private String id;
    private List<String> favorites;
    private String password;
    private String name;

    public User(String userId, List<String> recipeId, String name, String password) {
        this.id = userId;
        this.favorites = recipeId;
        this.name = name;
        this.password = password;


    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getRecipeId() {
        return favorites;
    }

    public void setRecipeId(List<String> recipeId) {
        this.favorites = recipeId;
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
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(favorites, user.favorites) && Objects.equals(password, user.password) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, favorites, password, name);
    }
}