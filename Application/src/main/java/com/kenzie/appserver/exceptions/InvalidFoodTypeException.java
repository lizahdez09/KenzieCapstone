package com.kenzie.appserver.exceptions;

public class InvalidFoodTypeException extends RuntimeException {

    public InvalidFoodTypeException(String message) {
        super(message);
    }
}
