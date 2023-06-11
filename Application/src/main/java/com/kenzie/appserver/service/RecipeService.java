package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Recipe recipeFromCrud = recipeRepository.findById(id)
                .map(Recipe::new)
                .orElse(null);
        return recipeFromCrud;
    }

    public Recipe addNewRecipe(RecipeCreateRequest request) {
        RecipeRecord record = createRecipeRecordFromRequest(request);
        recipeRepository.save(record);
        return new Recipe(record);
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
