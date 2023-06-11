package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.UserData;
import com.kenzie.capstone.service.model.UserRecord;

import java.util.List;

public class UserDao {

    private DynamoDBMapper mapper;

    public UserDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public UserData storeUserData(UserData userData) {
        try {
            mapper.save(userData, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "id",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id has already been used");
        }

        return userData;
    }

    public UserRecord getUserData(String id) {
        return mapper.load(UserRecord.class, id);
    }

    public UserRecord setUserData(String id, String favoriteRecipes, String name) {
        UserRecord userRecord = new UserRecord();
        userRecord.setId(id);
        userRecord.setFavoriteRecipes(favoriteRecipes);
        userRecord.setName(name);

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
}
