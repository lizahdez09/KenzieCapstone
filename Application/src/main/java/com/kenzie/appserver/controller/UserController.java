package com.kenzie.appserver.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kenzie.capstone.service.model.UserLoginRequest;
import com.kenzie.capstone.service.client.UserServiceClient;
import com.kenzie.capstone.service.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserServiceClient userService;

    UserController(UserServiceClient userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> getUser(@RequestBody UserLoginRequest userLoginRequest) {
        UserResponse user = userService.getUserData(userLoginRequest.getEmail());

        if (user == null) {
            return ResponseEntity.noContent().build();
        }

        if (user.getPassword().equalsIgnoreCase(userLoginRequest.getPassword())) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<UserResponse> addNewUser(@RequestBody UserRequest userCreateRequest) {
        UserResponse user = userService.setUserData(userCreateRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("user/update")
    public ResponseEntity<UserResponse> updateUserFavoriteRecipes(@RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            UserResponse updatedUser = userService.updateUserFavoriteRecipes(userUpdateRequest);
            return ResponseEntity.ok(updatedUser);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}