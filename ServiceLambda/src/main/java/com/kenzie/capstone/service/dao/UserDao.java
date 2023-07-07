package com.kenzie.capstone.service.dao;

import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserUpdateRequest;

public interface UserDao {

    UserRecord getUserByEmail(String email);

    UserRecord updateUser (UserUpdateRequest record);

    UserRecord createNewUser(UserRequest request);
}
