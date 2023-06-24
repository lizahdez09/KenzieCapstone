package com.kenzie.appserver.service.model;

import com.kenzie.appserver.exceptions.InvalidFoodTypeException;

public enum FoodType {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    DESSERT("Dessert");

    private String value;

    FoodType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FoodType fromValue(String value) {
        for (FoodType foodType : FoodType.values()) {
            if (foodType.value.equalsIgnoreCase(value)) {
                return foodType;
            }
        }
        throw new InvalidFoodTypeException("Invalid FoodType value: " + value);
    }
}
