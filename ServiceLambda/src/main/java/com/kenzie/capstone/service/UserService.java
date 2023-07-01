package com.kenzie.capstone.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.UUID;

public class UserService {
    private UserDao userDao;
    static final Logger log = LogManager.getLogger();

    @Inject
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserRecord getUserData(String id) {
        UserRecord record = userDao.getUserById(id);
        log.info("record- " + record);
        if (record != null && id.equals(record.getId())) {
            return record;
        } else {
            throw new RuntimeException("No UserData found");
        }
    }
    //not compatible with JSON


    public User setUserData(String id, String name, String password, String email, String favoriteRecipes) {
        UserRecord record = userDao.createNewUser(id, name, password, email, favoriteRecipes);

        return new User(record.getId(), record.getName(),
                record.getPassword(), record.getEmail());
    }

    public User updateUserData(String id, String name, String password, String email) {
        UserRecord record = userDao.getUserById(id);
        if (record == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }
        record.setName(name);
        record.setPassword(password);
        record.setEmail(email);
        userDao.updateUser(record);
        return new User(record.getId(), record.getName(),
                record.getPassword(), record.getEmail(),
                record.getFavoriteRecipes());
    }
}