package com.kenzie.appserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.client.UserServiceClient;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserResponse;
import com.kenzie.capstone.service.model.UserUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private ObjectMapper mapper = new ObjectMapper();
    private UserServiceClient userServiceClient;

    public UserService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

/*    public User getById(String id) {
        return userServiceClient.getUserData(id);
    }*/

   public UserResponse addNewUser(UserRequest userRequest){
        userRequest.setId(UUID.randomUUID().toString());
        return userServiceClient.setUserData(userRequest);
    }

    public User updateUser(String id, UserUpdateRequest userUpdateRequest) {
//        User existingUser = userServiceClient.getUserData(id);
//        if (existingUser == null) {
//            throw new RuntimeException("User not found with id: " + id);
//        }
//
//        // Update the existing user object with the new data
//        existingUser.setName(userUpdateRequest.getId());
//        existingUser.setEmail(userUpdateRequest.getEmail());
//        existingUser.setName(userUpdateRequest.getName());
//        existingUser.setPassword(userUpdateRequest.getPassword());
//        List<String> list = new ArrayList<>(Arrays.asList(userUpdateRequest.getFavoriteRecipes().split(",")));
//        existingUser.setFavoriteRecipes(list);
//
//        String updatedUserJson;
//        try {
//            updatedUserJson = mapper.writeValueAsString(existingUser);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }

        return null;
    }
}
