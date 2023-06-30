package com.kenzie.capstone.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRecord;

import javax.inject.Inject;
import java.util.UUID;

public class UserService {
    private UserDao userDao;

    @Inject
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserData(String id) {
        UserRecord record = userDao.getUserById(id);
        if (record != null && id.equals(record.getId())) {
            User user = new User(record.getId(), record.getEmail(),
                    record.getName(), record.getPassword(),
                    record.getFavoriteRecipes());
            return user;
        } else {
            throw new RuntimeException("No UserData found");
        }
    }


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