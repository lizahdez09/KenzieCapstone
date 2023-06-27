package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRecord;

public class UserDao {

    private DynamoDBMapper mapper;

    public UserDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public User storeUserData(User user) {
        try {
            mapper.save(user, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "id",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id has already been used");
        }

        return user;
    }

    public UserRecord getUserData(String id) {
        return mapper.load(UserRecord.class, id);
    }

    public UserRecord setUserData(String id, String name,String password,String email) {
        UserRecord userRecord = new UserRecord();
        userRecord.setId(id);
        userRecord.setEmail(email);
        userRecord.setName(name);
        userRecord.setPassword(password);

        try {
            mapper.save(userRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "id",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id already exists");
        }

        return userRecord;
    }

    public void updateUserData(UserRecord record) {
        try {
            mapper.save(record, new DynamoDBSaveExpression());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to update user data: " + e.getMessage());
        }
    }
}
