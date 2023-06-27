package com.kenzie.capstone.service.model;

import java.util.List;
import java.util.Objects;

public class User {

    private String id;
    // private List<String> favorites;
    private String password;
    private String name;
    private String email;

    public User(String userId, String name, String password, String email) {
        this.id = userId;
        this.name = name;
        this.password = password;
        this.email = email;

    }

    public User() {
    }

    public String getId() {
        return id;
    }

//    public void setFavorites(List<String> favorites) {
//        this.favorites = favorites;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public List<String> getFavorites() {
//        return favorites;
//    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public List<String> getRecipeId() {
//        return favorites;
//    }
//
//    public void setRecipeId(List<String> recipeId) {
//        this.favorites = recipeId;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, name, email);
    }
}
