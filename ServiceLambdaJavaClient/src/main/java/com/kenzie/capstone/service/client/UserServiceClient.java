package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.UserData;

public class UserServiceClient {

    private static final String GET_RECIPE_ENDPOINT = "recipe/{userId}";
    private static final String SET_RECIPE_ENDPOINT = "recipe";
    private static final String UPDATE_RECIPE_ENDPOINT = "recipe/{userId}";

    private ObjectMapper mapper;

    public UserServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public UserData getUserData(String userId) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_RECIPE_ENDPOINT.replace("{userId}", userId));
        UserData user;
        try {
            user = mapper.readValue(response, UserData.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return user;
    }

    public UserData setUserData(String data) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.postEndpoint(SET_RECIPE_ENDPOINT, data);
        UserData user;
        try {
            user = mapper.readValue(response, UserData.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return user;
    }

    public UserData updateUserData(String userId, String recipeId) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String updateEndpoint = UPDATE_RECIPE_ENDPOINT.replace("{userId}", userId);
        String response = endpointUtility.updateEndpoint(updateEndpoint, recipeId);
        UserData user;
        try {
            user = mapper.readValue(response, UserData.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to deserialize JSON: " + e.getMessage());
        }
        return user;
    }
}
