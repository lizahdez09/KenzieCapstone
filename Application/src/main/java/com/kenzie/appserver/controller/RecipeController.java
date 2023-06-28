package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.controller.model.RecipeResponse;
import com.kenzie.appserver.controller.model.RecipeUpdateRequest;
import com.kenzie.appserver.exceptions.RecipeNotFoundException;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private RecipeService recipeService;

    RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @GetMapping
    public ResponseEntity<List<RecipeResponse>> getAllRecipe() {
        List<RecipeResponse> responseList = new ArrayList<>();

        List<Recipe> recipeList = recipeService.getAllRecipes();
        for (Recipe recipe : recipeList) {
            responseList.add(createRecipeResponseFromRecipe(recipe));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<List<RecipeResponse>> getRecipeContainsIngredient(@PathVariable("id") String id) {
        List<RecipeResponse> responseList = new ArrayList<>();

        List<Recipe> recipeList = recipeService.getRecipeContainsIngredient(id);
        for (Recipe recipe : recipeList) {
            responseList.add(createRecipeResponseFromRecipe(recipe));
        }

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> get(@PathVariable("id") String id) {
        Recipe recipe = recipeService.getRecipeById(id);

        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createRecipeResponseFromRecipe(recipe));
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> addNewRecipe(@RequestBody RecipeCreateRequest recipeCreateRequest) {
        Recipe recipe = recipeService.addNewRecipe(recipeCreateRequest);

        return ResponseEntity.ok(createRecipeResponseFromRecipe(recipe));
    }
    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> updateRecipe(@PathVariable("id") String id, @RequestBody RecipeUpdateRequest recipeUpdateRequest) {
        try {
            Recipe updatedRecipe = recipeService.updateRecipe(id, recipeUpdateRequest);
            RecipeResponse recipeResponse = createRecipeResponseFromRecipe(updatedRecipe);
            return ResponseEntity.ok(recipeResponse);
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/favorite")
    public ResponseEntity<RecipeResponse> incrementFavoriteCount(@PathVariable("id") String id) {
        Recipe updatedRecipe = recipeService.incrementFavoriteCount(id);
        RecipeResponse recipeResponse = createRecipeResponseFromRecipe(updatedRecipe);
        return ResponseEntity.ok(recipeResponse);
    }

    /**
     * Creates a RecipeResponse from a Recipe
     * @param recipe {@link Recipe}
     * @return response - {@link RecipeResponse}
     */
    private RecipeResponse createRecipeResponseFromRecipe(Recipe recipe) {
        RecipeResponse response = new RecipeResponse();
        response.setId(recipe.getId());
        response.setName(recipe.getName());
        response.setFoodType(recipe.getFoodTypeAsString());
        response.setIngredients(recipe.getIngredientsAsString());
        response.setTimeToPrepare(recipe.getTimeToPrepare());
        return response;
    }
}
