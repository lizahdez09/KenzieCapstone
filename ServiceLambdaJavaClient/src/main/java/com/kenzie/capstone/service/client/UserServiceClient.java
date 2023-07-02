package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserResponse;
import com.kenzie.capstone.service.model.UserUpdateRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.util.UUID.randomUUID;

public class UserServiceClient {

    private static final String GET_USER_ENDPOINT = "user/{email}";
    private static final String SET_USER_ENDPOINT = "/user";
    private static final String UPDATE_USER_ENDPOINT = "user/{userId}";
    static final Logger log = LogManager.getLogger();
    private ObjectMapper mapper;

    public UserServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public UserResponse getUserData(String email) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_USER_ENDPOINT.replace("{email}", email));
        UserResponse user;
        try {
            user = mapper.readValue(response, UserResponse.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return user;
    }

    public UserResponse setUserData(UserRequest userRequest) {
        userRequest.setId(randomUUID().toString());
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

    public User updateUserData(String userId, UserUpdateRequest userUpdateRequest) {
/*        EndpointUtility endpointUtility = new EndpointUtility();
        String updateEndpoint = UPDATE_USER_ENDPOINT.replace("{userId}", userId);
        String response = endpointUtility.updateEndpoint(updateEndpoint, recipeId);
        User user;
        try {
            user = mapper.readValue(response, User.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to deserialize JSON: " + e.getMessage());
        }*/
        return null;
    }
}
