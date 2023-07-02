package com.kenzie.appserver.controller;

import com.kenzie.appserver.exceptions.RecipeNotFoundException;
import com.kenzie.capstone.service.client.UserServiceClient;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserResponse;
import com.kenzie.capstone.service.model.UserUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/user")
public class UserController {

    private UserServiceClient userService;
    UserController(UserServiceClient userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("email") String email) {

        UserResponse user = userService.getUserData(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserResponse> addNewUser(@RequestBody UserRequest userCreateRequest) {
        UserResponse user = userService.setUserData(userCreateRequest);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            User updateUser = userService.updateUserData(id, userUpdateRequest);
            UserResponse userResponse = createUserResponseFromUser(updateUser);
            return ResponseEntity.ok(userResponse);
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private UserResponse createUserResponseFromUser(User user) {
        UserResponse response = new UserResponse();
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setPassword(user.getPassword());
        return response;
    }
}