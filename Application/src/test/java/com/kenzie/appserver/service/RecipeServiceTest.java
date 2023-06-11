package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecipeServiceTest {

    private RecipeRepository recipeRepository;
    private RecipeService recipeService;

    @BeforeEach
    void setup() {
        recipeRepository = mock(RecipeRepository.class);
        recipeService = new RecipeService(recipeRepository);
    }
    /** ------------------------------------------------------------------------
     *  recipeService.getRecipeById
     *  ------------------------------------------------------------------------ **/

    @Test
    void getRecipeById_ReturnsRecipe() {
        //GIVEN
        String id = randomUUID().toString();
        RecipeRecord record = createRecipeRecordWithId(id);

        //WHEN
        when(recipeRepository.findById(id)).thenReturn(Optional.of(record));
        Recipe recipe = recipeService.getRecipeById(id);

        //THEN
        assertNotNull(recipe, "The Recipe is returned");
        Assertions.assertEquals(record.getId(), recipe.getId(), "The id's match");
        Assertions.assertEquals(record.getName(), recipe.getName(), "The names match");
    }

    @Test
    void getRecipeById_Returns() {
        //GIVEN
        String id = randomUUID().toString();
        RecipeRecord record = createRecipeRecordWithId(id);

        //WHEN
        when(recipeRepository.findById(id)).thenReturn(Optional.of(record));
        Recipe recipe = recipeService.getRecipeById(id);

        //THEN
        assertNotNull(recipe, "The Recipe is returned");
        Assertions.assertEquals(record.getId(), recipe.getId(), "The id's match");
        Assertions.assertEquals(record.getName(), recipe.getName(), "The names match");
    }

    private RecipeRecord createRecipeRecordWithId(String id) {
        RecipeRecord record = new RecipeRecord();
        record.setRecipeId(id);
        record.setRecipeName("Record");
        record.setIngredients("Ingredients");
        record.setTimeToPrepare("30");
        return record;
    }

        @Test
        public void addedRecipe_isNotNull() {
            RecipeCreateRequest request = new RecipeCreateRequest();
            RecipeService recipeService = new RecipeService(recipeRepository);
            Recipe result = recipeService.addNewRecipe(request);
            assertNotNull(result, "The added recipe should not be null");
        }

    }



