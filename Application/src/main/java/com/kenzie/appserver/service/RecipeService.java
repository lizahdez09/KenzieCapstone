package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.service.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;
    private List<Recipe> recipes;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe getRecipeById(String id) {
        for (Recipe recipe : recipes) {
            if (recipe.equals(id)) { // needs to do .getById when its finished
                return recipe;
            }
        }
        return null;
    }

    public Recipe addNewRecipe(String recipe) {

        Recipe newRecipe = new Recipe(recipe);
        recipes.add(newRecipe);
        return newRecipe;
    }



}
