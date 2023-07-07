package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.UserService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserUpdateRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UpdateUserData implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger log = LogManager.getLogger();
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        log.info(gson.toJson(input));

        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        UserService userService = serviceComponent.provideLambdaService();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent().withHeaders(headers);

        UserUpdateRequest userUpdateRequest = gson.fromJson(input.getBody(), UserUpdateRequest.class);
        try {
            // Update user data
            UserRecord userData = userService.updateUserFavoriteRecipes(userUpdateRequest);
            String output = gson.toJson(userData);
            response.setStatusCode(200);
            response.setBody(output);
        } catch (NotFoundException e) {
            response.setStatusCode(404);
            response.setBody(gson.toJson("User not found: " + e.getMessage()));
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setBody(gson.toJson("Update failed: " + e.getMessage()));
        }

        return response;
    }
}
