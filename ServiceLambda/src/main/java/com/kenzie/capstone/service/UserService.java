package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.model.ExampleRecord;
import com.kenzie.capstone.service.model.UserData;
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

    public UserData getUserData(String id) {
        UserRecord record = userDao.getUserData(id);
        if (record.getId().equals(id)) {
            UserData userData = new UserData();
            userData.setUserId(record.getId());
            List<String> recipeList = Arrays.stream(record.getFavoriteRecipes().split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            userData.setRecipeId(recipeList);
            userData.setName(record.getName());
            return userData;
        }
        return null;
    }

    public UserData setUserData(String data) {
        String id = UUID.randomUUID().toString();
        UserRecord record = userDao.setUserData(id, data, "Unknown");
        return new UserData(id, record.getFavoriteRecipes(), record.getName());
    }

    public UserData updateUserData(String id, String data) {
        UserRecord record = userDao.setUserData(id, data, "Unknown");
        return new UserData(id, record.getFavoriteRecipes(), record.getName());
    }
}