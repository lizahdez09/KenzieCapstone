package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.controller.model.RecipeResponse;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private RecipeService recipeService;

    RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> get(@PathVariable("id") String id) {
        Recipe recipe = recipeService.getRecipeById(id);

        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createRecipeResponseFromRecord(recipe));
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> addNewRecipe(@RequestBody RecipeCreateRequest recipeCreateRequest) {
        Recipe recipe = recipeService.addNewRecipe(recipeCreateRequest);

        return ResponseEntity.ok(createRecipeResponseFromRecord(recipe));
    }

    /**
     * Creates a RecipeResponse from a Recipe
     * @param recipe {@link Recipe}
     * @return response - {@link RecipeResponse}
     */
    private RecipeResponse createRecipeResponseFromRecord(Recipe recipe) {
        RecipeResponse response = new RecipeResponse();
        response.setId(recipe.getId());
        response.setName(recipe.getName());
        response.setIngredients(recipe.getIngredients());
        response.setTimeToPrepare(response.getTimeToPrepare());
        return response;
    }
}
