package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.exceptions.RecipeNotFoundException;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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
     *  recipeService.getAllRecipes
     *  ------------------------------------------------------------------------ **/
    @Test
    void getAllRecipes_returnsThreeRecipesInList() {
        //GIVEN
        String id1 = randomUUID().toString();
        String id2 = randomUUID().toString();
        String id3 = randomUUID().toString();

        RecipeRecord record1 = createRecipeRecordWithId(id1);
        RecipeRecord record2 = createRecipeRecordWithId(id2);
        RecipeRecord record3 = createRecipeRecordWithId(id3);

        List<RecipeRecord> recipeRecordList = new ArrayList<>();
        recipeRecordList.add(record1);
        recipeRecordList.add(record2);
        recipeRecordList.add(record3);

        List<Recipe> expected = new ArrayList<>();
        expected.add(new Recipe(record1));
        expected.add(new Recipe(record2));
        expected.add(new Recipe(record3));

        //WHEN
        when(recipeRepository.findAll()).thenReturn(recipeRecordList);
        List<Recipe> actual = recipeService.getAllRecipes();

        //THEN
        Assertions.assertNotNull(actual, "List is not null");
        Assertions.assertEquals(expected.size(), actual.size(), "List size is the same");
        Assertions.assertEquals(expected.get(0).getId(), actual.get(0).getId(), "First recipe id matches");
        Assertions.assertEquals(expected.get(1).getId(), actual.get(1).getId(), "Second recipe id matches");
        Assertions.assertEquals(expected.get(2).getId(), actual.get(2).getId(), "Third recipe id matches");
    }

    /** ------------------------------------------------------------------------
     *  recipeService.getRecipeById
     *  ------------------------------------------------------------------------ **/
    @Test
    void getRecipeById_returnsRecipe() {
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
    void getRecipeById_throwsRecipeNotFoundException() {
        //GIVEN
        String id = randomUUID().toString();

        //WHEN
        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        //THEN
        Assertions.assertThrows(RecipeNotFoundException.class, () -> recipeService.getRecipeById(id));
    }

    /** ------------------------------------------------------------------------
     *  recipeService.addNewRecipe
     *  ------------------------------------------------------------------------ **/
    @Test
    public void addNewRecipe_returnsRecipe() {
        //GIVEN
        String id = randomUUID().toString();
        RecipeRecord record = createRecipeRecordWithId(id);

        RecipeCreateRequest request = new RecipeCreateRequest();
        request.setName("Record");
        request.setIngredients("Ingredients");
        request.setTimeToPrepare("30");

        //WHEN
        when(recipeRepository.save(record)).thenReturn(record);
        Recipe recipe = recipeService.addNewRecipe(request);

        //THEN
        assertNotNull(recipe, "The Recipe is returned");
        Assertions.assertEquals(record.getName(), recipe.getName(), "The names match");
    }

    /**
     * Creates a RecipeRecord with a given String id
     * @param id {@link String}
     * @return record - {@link RecipeRecord}
     */
    private RecipeRecord createRecipeRecordWithId(String id) {
        RecipeRecord record = new RecipeRecord();
        record.setRecipeId(id);
        record.setRecipeName("Record");
        record.setIngredients("Ingredients");
        record.setTimeToPrepare("30");
        return record;
    }
}



