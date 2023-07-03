package com.kenzie.capstone.service.dao;

import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserRequest;

public class CachingUserDao implements UserDaoInterface{
    @Override
    public User storeUserData(User user) {
        return null;
    }

    @Override
    public UserRecord getUserByEmail(String email) {
        return null;
    }

    @Override
    public void updateUser(UserRecord record) {

    }

    @Override
    public UserRecord createNewUser(UserRequest request) {
        return null;
    }
}
