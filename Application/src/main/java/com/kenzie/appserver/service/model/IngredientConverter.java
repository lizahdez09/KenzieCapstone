package com.kenzie.appserver.service.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class IngredientConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String ingredientsToJson(List<RecipeIngredient> recipeIngredients) {
        try {
            return objectMapper.writeValueAsString(recipeIngredients);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static List<RecipeIngredient> jsonToIngredients(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<RecipeIngredient>>() {});
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
