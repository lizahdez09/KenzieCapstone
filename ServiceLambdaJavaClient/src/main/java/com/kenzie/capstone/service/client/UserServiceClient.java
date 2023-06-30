package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.User;

public class UserServiceClient {

    private static final String GET_RECIPE_ENDPOINT = "user/{userId}";
    private static final String SET_RECIPE_ENDPOINT = "user";
    private static final String UPDATE_RECIPE_ENDPOINT = "user/{userId}";

    private ObjectMapper mapper;

    public UserServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public User getUserData(String userId) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_RECIPE_ENDPOINT.replace("{userId}", userId));
        User user;
        try {
            user = mapper.readValue(response, User.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return user;
    }

    public User setUserData(String data) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.postEndpoint(SET_RECIPE_ENDPOINT, data);
        User user;
        try {
            user = mapper.readValue(response, User.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return user;
    }

    public User updateUserData(String userId, String recipeId) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String updateEndpoint = UPDATE_RECIPE_ENDPOINT.replace("{userId}", userId);
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
