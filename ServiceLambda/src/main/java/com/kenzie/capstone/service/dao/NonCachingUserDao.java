package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserUpdateRequest;

import java.util.ArrayList;

public class NonCachingUserDao {

    private DynamoDBMapper mapper;

    public NonCachingUserDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
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
        userRecord.setFavoriteRecipes(new ArrayList<>());

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

    public UserRecord updateUser(UserUpdateRequest userUpdateRequest, UserRecord userRecord) {
        userRecord.setFavoriteRecipes(userUpdateRequest.getFavoriteRecipes());
        try {
            mapper.save(userRecord, new DynamoDBSaveExpression());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to update user data: " + e.getMessage());
        }
        return userRecord;
    }
}
