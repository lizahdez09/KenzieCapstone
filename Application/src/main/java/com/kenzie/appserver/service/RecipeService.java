package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.service.model.Recipe;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe findById(String id) {
        return null;
    }

    public Recipe addNewRecipe(String recipe) {
        return null;
    }
}
