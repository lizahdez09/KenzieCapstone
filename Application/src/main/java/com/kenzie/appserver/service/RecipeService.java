package com.kenzie.appserver.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.controller.model.RecipeUpdateRequest;
import com.kenzie.appserver.exceptions.RecipeNotFoundException;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@Service
public class RecipeService {
    private ObjectMapper mapper = new ObjectMapper();
    private RecipeRepository recipeRepository;
    private IngredientService ingredientService;

    public RecipeService(RecipeRepository recipeRepository, IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        Iterable<RecipeRecord> recordIterable = recipeRepository.findAll();
        for (RecipeRecord record : recordIterable) {
            recipes.add(new Recipe(record));
        }
        return recipes;
    }

    public Recipe getRecipeById(String id) {
        return recipeRepository.findById(id)
                .map(Recipe::new)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found!"));
    }

    public List<Recipe> getRecipeByType(String type) {
        return getAllRecipes().stream()
                .filter(recipe -> recipe.getFoodTypeAsString().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<Recipe> getRecipeByTime(int time) {
        return getAllRecipes().stream()
                .filter(recipe -> {
                    String recipeTime = recipe.getTimeToPrepare();
                    int recipeNumericTime = Integer.parseInt(recipeTime.replace(" minutes", ""));
                    return recipeNumericTime <= time;
                })
                .collect(Collectors.toList());
    }

    public String getIngredientIdByName(String name) {
        Ingredient ingredient = ingredientService.getOrCreateIngredient(name);
        return ingredient.getId();
    }

    public List<Recipe> getRecipeContainsIngredient(List<String> ingredientIds) {
        return getAllRecipes().stream()
                .filter(recipe -> ingredientIds.stream()
                        .allMatch(ingredientId -> recipe.getIngredientsAsList().stream()
                                .anyMatch(ingredient -> ingredient.getId().equals(ingredientId))))
                .collect(Collectors.toList());
    }

    public Recipe addNewRecipe(RecipeCreateRequest request) {
        List<RecipeIngredient> recipeIngredients = checkIngredientsExist(request.getIngredients());

        RecipeRecord record = createRecipeRecordFromRequest(request);
        record.setIngredients(RecipeIngredientConverter.ingredientsToJson(recipeIngredients));
        recipeRepository.save(record);
        return new Recipe(record);
    }


    public Recipe updateRecipe(String id, RecipeUpdateRequest request) {
        Recipe existingRecipe = getRecipeById(id);

        List<RecipeIngredient> recipeIngredients = checkIngredientsExist(request.getIngredients());

        existingRecipe.setName(request.getName());
        existingRecipe.setFoodType(FoodType.fromValue(request.getFoodType()));
        existingRecipe.setIngredients(RecipeIngredientConverter.ingredientsToJson(recipeIngredients));
        existingRecipe.setTimeToPrepare(request.getTimeToPrepare());
        existingRecipe.setInstructions(request.getInstructions());

        RecipeRecord record = createRecipeRecordFromRecipe(existingRecipe);
        recipeRepository.save(record);

        return new Recipe(record);
    }

    public Recipe incrementFavoriteCount(String id) {
        Recipe recipe = getRecipeById(id);
        recipe.increaseFavoriteCount();

        RecipeRecord record = createRecipeRecordFromRecipe(recipe);
        recipeRepository.save(record);

        return recipe;
    }

    public Recipe decrementFavoriteCount(String id) {
        Recipe recipe = getRecipeById(id);
        recipe.decreaseFavoriteCount();

        RecipeRecord record = createRecipeRecordFromRecipe(recipe);
        recipeRepository.save(record);

        return recipe;
    }


    private RecipeRecord createRecipeRecordFromRecipe(Recipe recipe) {
        RecipeRecord record = new RecipeRecord();
        record.setId(recipe.getId());
        record.setName(recipe.getName());
        record.setFoodType(recipe.getFoodTypeAsString());
        record.setIngredients(recipe.getIngredientsAsString());
        record.setTimeToPrepare(recipe.getTimeToPrepare());
        record.setInstructions(recipe.getInstructions());
        record.setFavoriteCount(recipe.getFavoriteCount());
        return record;
    }

    /**
     * Creates a RecipeRecord from a RecipeCreateRequest
     * @param request {@link RecipeCreateRequest}
     * @return record - {@link RecipeRecord}
     */
    private RecipeRecord createRecipeRecordFromRequest(RecipeCreateRequest request) {
        RecipeRecord record = new RecipeRecord();
        if (request.getId() == null || request.getId().isEmpty()) {
            record.setId(randomUUID().toString());
        } else {
            record.setId(request.getId());
        }
        record.setName(request.getName());
        record.setFoodType(request.getFoodType());
        record.setTimeToPrepare(request.getTimeToPrepare());
        record.setInstructions(request.getInstructions());
        record.setFavoriteCount(0);
        return record;
    }

    private List<RecipeIngredient> checkIngredientsExist(String jsonIngredientList) {
        JsonNode ingredientsJson;
        try {
            ingredientsJson = mapper.readTree(jsonIngredientList);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid ingredients JSON format");
        }
        if (!ingredientsJson.isArray()) {
            throw new IllegalArgumentException("Invalid ingredients JSON format. Expected an array.");
        }

        List<RecipeIngredient> recipeIngredients = new ArrayList<>();

        for (JsonNode ingredientJson : ingredientsJson) {
            String ingredientName = ingredientJson.get("name").asText();
            Ingredient ingredient = ingredientService.getOrCreateIngredient(ingredientName);

            String amount = ingredientJson.get("amount").asText();
            String measurement = ingredientJson.get("measurement").asText();

            RecipeIngredient recipeIngredient = new RecipeIngredient(ingredient.getId(), ingredient.getName(), amount, measurement);
            recipeIngredients.add(recipeIngredient);
        }
        return recipeIngredients;
    }
}
