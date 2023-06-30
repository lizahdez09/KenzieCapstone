package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.UserService;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SetUserData implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        log.info(gson.toJson(input));

        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        UserService userLambService = serviceComponent.provideLambdaService();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        // Extract the data from the request event
//        String id = input.getQueryStringParameters().get("id");
//        String name = input.getQueryStringParameters().get("name");
//        String password = input.getQueryStringParameters().get("password");
//        String email = input.getQueryStringParameters().get("email");
//        String favoriteRecipes = input.getQueryStringParameters().get("favoriteRecipes");

        UserRequest userRequest = gson.fromJson(input.getBody(),UserRequest.class);
        if (userRequest.getName() == null || userRequest.getEmail() == null || userRequest.getPassword() == null) {
            return response
                    .withStatusCode(400)
                    .withBody("data is invalid");
        }

        try {
            User userData = userLambService.setUserData(userRequest.getId(), userRequest.getName(),
                    userRequest.getPassword(), userRequest.getEmail(), userRequest.getFavoriteRecipes());
            String output = gson.toJson(userRequest);
            //User data needs to be converted to a userRequest to be able to be serialized by JSON
            return response
                    .withStatusCode(200)
                    .withBody(output);

        } catch (Exception e) {
            return response
                    .withStatusCode(400)
                    .withBody(gson.toJson(e.getMessage()));
        }
    }
}


//kinda patch work - but before its turn into a user - we have to turn it into a userResponse
//wasnt working because of JSON - not compatible , did not know how to translate // backend is set up so that they only need those 3 inputs