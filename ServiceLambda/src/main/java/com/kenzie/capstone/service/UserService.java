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


    public UserRecord getUserData(String email) {
        return this.userDao.getUserByEmail(email);
    }


    public UserRecord setUserData(UserRequest request) {
        UserRecord record = this.userDao.createNewUser(request);
        return record;
    }

    public UserRecord updateUserFavoriteRecipes(UserUpdateRequest userUpdateRequest) {
        return userDao.updateUser(userUpdateRequest);
    }
}