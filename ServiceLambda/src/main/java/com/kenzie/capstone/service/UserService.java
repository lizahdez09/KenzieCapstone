package com.kenzie.capstone.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRecord;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserService {
    private UserDao userDao;

    @Inject
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserData(String id) {
        UserRecord record = userDao.getUserData(id);
        if (record.getId().equals(id)) {
            User user = new User();
            user.setUserId(record.getId());
            List<String> recipeList = Arrays.stream(record.getFavoriteRecipes().split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            user.setRecipeId(recipeList);
            user.setName(record.getName());
            return user;
        } else {
            throw new RuntimeException("No UserData found");
        }
    }

    public User setUserData(String recipes, String name) {
        String id = UUID.randomUUID().toString();
        UserRecord record = userDao.setUserData(id, recipes, name);
        List<String> recipeList = Arrays.stream(record.getFavoriteRecipes().split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        return new User(id, recipeList, record.getName());
    }

    public User updateUserData(String id, String recipes, String name) {
        UserRecord record = userDao.getUserData(id);
        if (record == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }

        record.setFavoriteRecipes(recipes);
        record.setName(name);
        userDao.updateUserData(record);

        List<String> recipeList = Arrays.stream(record.getFavoriteRecipes().split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        return new User(id, recipeList, record.getName());
    }
}