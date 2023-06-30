package com.kenzie.appserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.client.UserServiceClient;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private ObjectMapper mapper = new ObjectMapper();
    private UserServiceClient client;

    public UserService(UserServiceClient userServiceClient) {
        this.client = userServiceClient;
    }

    public User getById(String id) {
        return client.getUserData(id);
    }

    public User addNewUser(UserRequest userRequest){
        String userRequestJson;
        try {
            userRequestJson = mapper.writeValueAsString(userRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return client.setUserData(userRequestJson);
    }

    public User updateUser(String id, UserUpdateRequest userUpdateRequest) {
        User existingUser = client.getUserData(id);
        if (existingUser == null) {
            throw new RuntimeException("User not found with id: " + id);
        }

        // Update the existing user object with the new data
        existingUser.setName(userUpdateRequest.getId());
        existingUser.setEmail(userUpdateRequest.getEmail());
        existingUser.setName(userUpdateRequest.getName());
        existingUser.setPassword(userUpdateRequest.getPassword());
        List<String> list = new ArrayList<>(Arrays.asList(userUpdateRequest.getFavoriteRecipes().split(",")));
        existingUser.setFavoriteRecipes(list);

        String updatedUserJson;
        try {
            updatedUserJson = mapper.writeValueAsString(existingUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return client.setUserData(updatedUserJson);
    }
}
