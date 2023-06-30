package com.kenzie.appserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.client.UserServiceClient;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRequest;
import org.springframework.stereotype.Service;

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

}
