package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.controller.model.RecipeUpdateRequest;
import com.kenzie.appserver.exceptions.RecipeNotFoundException;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;
    private List<Recipe> recipes;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .map(Recipe::new)
                .collect(Collectors.toList());
    }

    public Recipe getRecipeById(String id) {
        return recipeRepository.findById(id)
                .map(Recipe::new)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found!"));
    }

    public Recipe addNewRecipe(RecipeCreateRequest request) {
        RecipeRecord record = createRecipeRecordFromRequest(request);
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

    private RecipeRecord createRecipeRecordFromRecipe(Recipe existingRecipe) {
        RecipeRecord record = new RecipeRecord();
        record.setRecipeId(existingRecipe.getId());
        record.setRecipeName(existingRecipe.getName());
        record.setIngredients(existingRecipe.getIngredientsAsString());
        record.setTimeToPrepare(existingRecipe.getTimeToPrepare());
        return record;
    }

    /**
     * Creates a RecipeRecord from a RecipeCreateRequest
     * @param request {@link RecipeCreateRequest}
     * @return record - {@link RecipeRecord}
     */
    private RecipeRecord createRecipeRecordFromRequest(RecipeCreateRequest request) {
        RecipeRecord record = new RecipeRecord();
        record.setRecipeId(UUID.randomUUID().toString());
        record.setRecipeName(request.getName());
        record.setIngredients(request.getIngredients());
        record.setTimeToPrepare(request.getTimeToPrepare());
        return record;
    }
}
