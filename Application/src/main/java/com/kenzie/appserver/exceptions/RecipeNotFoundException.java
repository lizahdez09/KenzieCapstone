package com.kenzie.appserver.exceptions;

public class RecipeNotFoundException extends RuntimeException{

    public RecipeNotFoundException(String message){
        super(message);
    }
}
