package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserResponse;

public class UserServiceClient {

    private static final String GET_USER_ENDPOINT = "user/{userId}";
    private static final String SET_USER_ENDPOINT = "/user";
    private static final String UPDATE_USER_ENDPOINT = "user/{userId}";

    private ObjectMapper mapper;

    public UserServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public User getUserData(String id) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_USER_ENDPOINT.replace("{userId}", id));
        User user;
        try {
            user = mapper.readValue(response, User.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return user;
    }

    public UserResponse setUserData(UserRequest userRequest) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String userRequestJson;
        try {
            userRequestJson = mapper.writeValueAsString(userRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String response = endpointUtility.postEndpoint(SET_USER_ENDPOINT, userRequestJson);
        UserResponse user;
        try {
            user = mapper.readValue(response, UserResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e.getMessage());
        }
        return user;
    }

    public User updateUserData(String userId, String recipeId) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String updateEndpoint = UPDATE_USER_ENDPOINT.replace("{userId}", userId);
        String response = endpointUtility.updateEndpoint(updateEndpoint, recipeId);
        User user;
        try {
            user = mapper.readValue(response, User.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to deserialize JSON: " + e.getMessage());
        }
        return user;
    }
}
