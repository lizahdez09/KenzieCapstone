package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.controller.model.RecipeUpdateRequest;
import com.kenzie.appserver.exceptions.RecipeNotFoundException;
import com.kenzie.appserver.repositories.IngredientRepository;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.IngredientRecord;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;
    private RecipeService recipeService;
    private IngredientService ingredientService;

    @BeforeEach
    void setup() {
        recipeRepository = mock(RecipeRepository.class);
        ingredientRepository = mock(IngredientRepository.class);
        ingredientService = new IngredientService(ingredientRepository);
        recipeService = new RecipeService(recipeRepository, ingredientService);
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
     *  recipeService.getRecipeContainsIngredient
     *  ------------------------------------------------------------------------ **/
    @Test
    void getRecipeContainsIngredient_returnsRecipe() {
        //GIVEN
        String id = randomUUID().toString();
        List<RecipeRecord> recordList = new ArrayList<>();
        RecipeRecord record = createRecipeRecordWithId(id);
        recordList.add(record);

        //WHEN
        when(recipeRepository.findAll()).thenReturn(recordList);
        List<Recipe> recipeList = recipeService.getRecipeContainsIngredient("1");

        //THEN
        assertNotNull(recipeList);
        assertEquals(record.getId(), recipeList.get(0).getId());
    }

    @Test
    void getRecipeContainsIngredient_returnsEmptyList() {
        //GIVEN
        String id = randomUUID().toString();
        List<RecipeRecord> recordList = new ArrayList<>();
        RecipeRecord record = createRecipeRecordWithId(id);
        recordList.add(record);

        //WHEN
        when(recipeRepository.findAll()).thenReturn(recordList);
        List<Recipe> recipeList = recipeService.getRecipeContainsIngredient("2");

        //THEN
        assertNotNull(recipeList);
        assertTrue(recipeList.isEmpty());
    }

    @Test
    void getRecipeContainsIngredient_returnsCertainRecipe() {
        //GIVEN
        String id = randomUUID().toString();
        String id1 = randomUUID().toString();
        List<RecipeRecord> recordList = new ArrayList<>();
        RecipeRecord record = createRecipeRecordWithId(id);
        recordList.add(record);
        RecipeRecord record1 = createRecipeRecordWithId(id1);
        record1.setIngredients(
                "[{\"id\":\"10\", \"name\":\"Ingredient\", \"amount\":\"1\", \"measurement\":\"TABLESPOON\"}]");

        //WHEN
        when(recipeRepository.findAll()).thenReturn(recordList);
        List<Recipe> recipeList = recipeService.getRecipeContainsIngredient("1");

        //THEN
        assertNotNull(recipeList);
        assertEquals(1, recipeList.size());
        assertEquals(record.getId(), recipeList.get(0).getId());
    }

    /** ------------------------------------------------------------------------
     *  recipeService.addNewRecipe
     *  ------------------------------------------------------------------------ **/
    @Test
    public void addNewRecipe_returnsRecipe() {
        //GIVEN
        String ingredientName = "Milk";
        String id = randomUUID().toString();
        RecipeRecord record = createRecipeRecordWithId(id);

        IngredientRecord ingredientRecord = createIngredientRecord(ingredientName);
        List<IngredientRecord> ingredientRecordList = new ArrayList<>();
        ingredientRecordList.add(ingredientRecord);

        RecipeIngredient recipeIngredient = createRecipeIngredient(ingredientRecord, "2", "CUP");
        List<RecipeIngredient> recipeIngredientList = new ArrayList<>();
        recipeIngredientList.add(recipeIngredient);

        RecipeCreateRequest request = new RecipeCreateRequest();
        request.setName("Record");
        request.setFoodType("Lunch");
        request.setIngredients(RecipeIngredientConverter.ingredientsToJson(recipeIngredientList));
        request.setTimeToPrepare("30");

        //WHEN
        when(ingredientRepository.findAll()).thenReturn(ingredientRecordList);
        when(recipeRepository.save(record)).thenReturn(record);
        Recipe recipe = recipeService.addNewRecipe(request);

        //THEN
        assertNotNull(recipe, "The Recipe is returned");
        Assertions.assertEquals(record.getName(), recipe.getName(), "The names match");
    }

    /** ------------------------------------------------------------------------
     *  recipeService.updateRecipe
     *  ------------------------------------------------------------------------ **/

    @Test
    public void testUpdateRecipe_ExistingRecipe_Success() {
        //GIVEN
        String id = randomUUID().toString();

        RecipeUpdateRequest updateRequest = createRecipeUpdateRequest();

        RecipeRecord oldRecord = createRecipeRecordWithId(id);

        RecipeRecord newRecord = new RecipeRecord();
        newRecord.setId(oldRecord.getId());
        newRecord.setName(updateRequest.getName());
        newRecord.setIngredients(updateRequest.getIngredients());
        newRecord.setTimeToPrepare(updateRequest.getTimeToPrepare());

        List<IngredientRecord> ingredientRecordList = new ArrayList<>();
        IngredientRecord ingredientRecord = new IngredientRecord();
        ingredientRecord.setId(randomUUID().toString());
        ingredientRecord.setName("Ingredient");
        ingredientRecordList.add(ingredientRecord);

        //WHEN
        when(ingredientRepository.findAll()).thenReturn(ingredientRecordList);
        when(recipeRepository.findById(id)).thenReturn(Optional.of(oldRecord));
        when(recipeRepository.save(any(RecipeRecord.class))).thenReturn(newRecord);

        Recipe updatedRecipe = recipeService.updateRecipe(id, updateRequest);
        //THEN
        Assertions.assertNotNull(updatedRecipe);
        Assertions.assertEquals(oldRecord.getId(), updatedRecipe.getId());
        Assertions.assertNotEquals(updatedRecipe.getIngredientsAsString(), oldRecord.getIngredients());
    }

    @Test
    public void testUpdateRecipe_NonExistingRecipe_ThrowsException() {
        // Given
        String recipeId = randomUUID().toString();
        RecipeUpdateRequest updateRequest = createRecipeUpdateRequest();

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> recipeService.updateRecipe(recipeId, updateRequest));
    }

    /**
     * Creates a RecipeRecord with a given String id
     * @param id {@link String}
     * @return record - {@link RecipeRecord}
     */
    private RecipeRecord createRecipeRecordWithId(String id) {
        RecipeRecord record = new RecipeRecord();
        record.setId(id);
        record.setName("Record");
        record.setFoodType("Lunch");
        record.setIngredients(jsonIngredient());
        record.setTimeToPrepare("30");
        record.setInstructions("Instructions");
        return record;
    }

    /**
     * Creates a RecipeUpdateRequest
     * @return request - {@link RecipeUpdateRequest}
     */
    private RecipeUpdateRequest createRecipeUpdateRequest(){
        RecipeUpdateRequest request = new RecipeUpdateRequest();
        request.setName("Updated Recipe");
        request.setFoodType("Lunch");
        request.setIngredients(jsonIngredient());
        request.setTimeToPrepare("30 minutes");
        request.setInstructions("Instructions");
        return request;
    }

    private String jsonIngredient() {
        return "[{\"id\":\"1\", \"name\":\"Ingredient\", \"amount\":\"1\", \"measurement\":\"TABLESPOON\"}]";
    }

    private IngredientRecord createIngredientRecord(String name){
        IngredientRecord record = new IngredientRecord();
        record.setId(randomUUID().toString());
        record.setName(name);
        return record;
    }

    private RecipeIngredient createRecipeIngredient(IngredientRecord record, String amount, String measurement) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(record.getId());
        recipeIngredient.setName(record.getName());
        recipeIngredient.setAmount(amount);
        recipeIngredient.setMeasurement(measurement);
        return recipeIngredient;
    }
}



