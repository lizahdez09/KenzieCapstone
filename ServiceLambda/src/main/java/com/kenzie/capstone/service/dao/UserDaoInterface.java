package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserRequest;

public interface UserDaoInterface {

    User storeUserData(User user);

    UserRecord getUserByEmail(String email);

    void updateUser (UserRecord record);

    UserRecord createNewUser(UserRequest request);
}
