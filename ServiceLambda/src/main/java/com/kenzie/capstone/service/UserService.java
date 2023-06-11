package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.model.ExampleRecord;
import com.kenzie.capstone.service.model.UserData;
import com.kenzie.capstone.service.model.UserRecord;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class UserService {
    private UserDao userDao;

    @Inject
    public UserService(UserDao userDao) {

        this.userDao = userDao;
    }

    public UserData getUserData(String id) {
        List<UserRecord> records = userDao.getUserData(id);
        if (records.size() > 0) {
            return new UserData(records.get(0).getId(), records.get(0).getData());
        }
        return null;
    }

    public UserData setUserData(String data) {
        String id = UUID.randomUUID().toString();
        UserRecord record = userDao.setUserData(id, data);
        return new UserData(id, data);
    }

    public UserData updateUserData (String id, String data){

        UserData userData = new UserData(id, data);
        return userData;
    }
}
