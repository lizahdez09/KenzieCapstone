package com.kenzie.capstone.service.model;

public class InvalidLogInCredentials extends RuntimeException {

    public InvalidLogInCredentials(String message) {
        super(message);
    }
}
