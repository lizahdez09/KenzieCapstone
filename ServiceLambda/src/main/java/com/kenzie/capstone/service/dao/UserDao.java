package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserRequest;

public class UserDao {

    private DynamoDBMapper mapper;

    public UserDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public User storeUserData(User user) {
        try {
            mapper.save(user, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "email",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("email has already been used");
        }

        return user;
    }

    public UserRecord getUserByEmail(String email) {
        return mapper.load(UserRecord.class, email);
    }

    public UserRecord createNewUser(UserRequest request) {
        UserRecord userRecord = new UserRecord();
        userRecord.setId(request.getId());
        userRecord.setName(request.getName());
        userRecord.setPassword(request.getPassword());
        userRecord.setEmail(request.getEmail());
        userRecord.setFavoriteRecipes(request.getFavoriteRecipes());

        try {
            mapper.save(userRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "email",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("email already exists");
        }

        return userRecord;
    }

    public void updateUser(UserRecord record) {
        try {
            mapper.save(record, new DynamoDBSaveExpression());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to update user data: " + e.getMessage());
        }
    }
}
