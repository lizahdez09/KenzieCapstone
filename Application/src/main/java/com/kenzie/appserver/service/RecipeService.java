package com.kenzie.appserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.controller.model.IngredientRequest;
import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.controller.model.RecipeUpdateRequest;
import com.kenzie.appserver.exceptions.RecipeNotFoundException;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Ingredient;
import com.kenzie.appserver.service.model.IngredientConverter;
import com.kenzie.appserver.service.model.Recipe;
import com.kenzie.appserver.service.model.RecipeIngredient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public Recipe addNewRecipe(RecipeCreateRequest request) {
        // TODO check if ingredient exists
        JsonNode ingredientsJson;
        try {
            ingredientsJson = mapper.readTree(request.getIngredients());
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

        RecipeRecord record = createRecipeRecordFromRequest(request);
        record.setIngredients(IngredientConverter.ingredientsToJson(recipeIngredients));
        recipeRepository.save(record);
        return new Recipe(record);
    }


    public Recipe updateRecipe(String id, RecipeUpdateRequest request) {
        Recipe existingRecipe = getRecipeById(id);
        if (existingRecipe == null) {
            throw new RecipeNotFoundException("Recipe not found!");
        }
        existingRecipe.setName(request.getName());
        existingRecipe.setIngredients(request.getIngredients());
        existingRecipe.setTimeToPrepare(request.getTimeToPrepare());
        RecipeRecord record = createRecipeRecordFromRecipe(existingRecipe);
        recipeRepository.save(record);
        return new Recipe(record);
    }

    private RecipeRecord createRecipeRecordFromRecipe(Recipe recipe) {
        RecipeRecord record = new RecipeRecord();
        record.setId(recipe.getId());
        record.setName(recipe.getName());
        record.setFoodType(recipe.getFoodTypeAsString());
        record.setIngredients(recipe.getIngredientsAsString());
        record.setTimeToPrepare(recipe.getTimeToPrepare());
        return record;
    }

    /**
     * Creates a RecipeRecord from a RecipeCreateRequest
     * @param request {@link RecipeCreateRequest}
     * @return record - {@link RecipeRecord}
     */
    private RecipeRecord createRecipeRecordFromRequest(RecipeCreateRequest request) {
        RecipeRecord record = new RecipeRecord();
        record.setId(randomUUID().toString());
        record.setName(request.getName());
        record.setFoodType(request.getFoodType());
        record.setTimeToPrepare(request.getTimeToPrepare());
        return record;
    }
}
