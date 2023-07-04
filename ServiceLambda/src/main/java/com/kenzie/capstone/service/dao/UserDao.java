package com.kenzie.capstone.service.dao;

import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserRequest;

public interface UserDao {

    UserRecord getUserByEmail(String email);

    UserRecord updateUser (UserRecord record);

    UserRecord createNewUser(UserRequest request);
}
