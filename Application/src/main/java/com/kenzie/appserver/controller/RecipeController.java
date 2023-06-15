package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.controller.model.RecipeResponse;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private RecipeService recipeService;

    RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @GetMapping
    public ResponseEntity<List<RecipeResponse>> getAllRecipe() {
        List<Recipe> recipeList = recipeService.getAllRecipes();
        List<RecipeResponse> responses = recipeList.stream()
                .map(this::createRecipeResponseFromRecipe)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
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

    /**
     * Creates a RecipeResponse from a Recipe
     * @param recipe {@link Recipe}
     * @return response - {@link RecipeResponse}
     */
    private RecipeResponse createRecipeResponseFromRecipe(Recipe recipe) {
        RecipeResponse response = new RecipeResponse();
        response.setId(recipe.getId());
        response.setName(recipe.getName());
        response.setIngredients(recipe.getIngredientsAsString());
        response.setTimeToPrepare(response.getTimeToPrepare());
        return response;
    }
}
