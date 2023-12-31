package com.kenzie.appserver.service.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class IngredientConverter {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String ingredientsToJson(List<Ingredient> recipeIngredients) {
        try {
            return mapper.writeValueAsString(recipeIngredients);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Ingredient> jsonToIngredients(String json) {
        try {
            return mapper.readValue(json, new TypeReference<List<Ingredient>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
