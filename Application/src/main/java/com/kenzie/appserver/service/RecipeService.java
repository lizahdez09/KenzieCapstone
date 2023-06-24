package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.controller.model.RecipeUpdateRequest;
import com.kenzie.appserver.exceptions.RecipeNotFoundException;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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
        record.setId(UUID.randomUUID().toString());
        record.setName(request.getName());
        record.setFoodType(request.getFoodType());
        record.setIngredients(request.getIngredients());
        record.setTimeToPrepare(request.getTimeToPrepare());
        return record;
    }
}
