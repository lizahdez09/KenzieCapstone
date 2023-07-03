package com.kenzie.capstone.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kenzie.capstone.service.dao.NonCachingUserDao;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UserService {
    private NonCachingUserDao nonCachingUserDao;
    static final Logger log = LogManager.getLogger();

    @Inject
    public UserService(NonCachingUserDao nonCachingUserDao) {
        this.nonCachingUserDao = nonCachingUserDao;
    }

    public UserRecord getUserData(String email) {
        UserRecord record = nonCachingUserDao.getUserByEmail(email);
        log.info("record- " + record);
        if (record != null && email.equals(record.getEmail())) {
            return record;
        } else {
            throw new RuntimeException("No UserData found");
        }
    }
    //not compatible with JSON


    public UserRecord setUserData(UserRequest request) {
        UserRecord record = nonCachingUserDao.createNewUser(request);
        return record;
    }

    public User updateUserData(String id, String name, String password, String email) {
        UserRecord record = nonCachingUserDao.getUserByEmail(id);
        if (record == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }
        record.setName(name);
        record.setPassword(password);
        record.setEmail(email);
        nonCachingUserDao.updateUser(record);
        return new User(record.getId(), record.getName(),
                record.getPassword(), record.getEmail(),
                record.getFavoriteRecipes());
    }
}