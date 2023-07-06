package com.kenzie.capstone.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserUpdateRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class UserService {
    private UserDao userDao;
    static final Logger log = LogManager.getLogger();

    @Inject
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserService() {}

    public UserRecord getUserData(String email) {
        UserRecord record = this.userDao.getUserByEmail(email);
        log.info("record- " + record);
        if (record != null && email.equals(record.getEmail())) {
            return record;
        } else {
            throw new RuntimeException("No UserData found");
        }
    }
    //not compatible with JSON


    public UserRecord setUserData(UserRequest request) {
        UserRecord record = this.userDao.createNewUser(request);
        return record;
    }

    public User updateUserFavoriteRecipes(UserUpdateRequest userUpdateRequest) {
        UserRecord user = userDao.getUserByEmail(userUpdateRequest.getEmail());
        if (user == null) {
            throw new NotFoundException("User not found with email: " + userUpdateRequest.getEmail());
        }

        user.setFavoriteRecipes(userUpdateRequest.getFavoriteRecipes());
        userDao.updateUser(user);

        // Only return the updated user's favorite recipes
        return new User(user.getId(), user.getEmail(), user.getName(), user.getPassword(), user.getFavoriteRecipes());
    }
}