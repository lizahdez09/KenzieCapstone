package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.IngredientCreateRequest;
import com.kenzie.appserver.repositories.IngredientRepository;
import com.kenzie.appserver.repositories.model.IngredientRecord;
import com.kenzie.appserver.service.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IngredientServiceTest {
    //TODO write tests

    private IngredientRepository ingredientRepository;

    private IngredientService ingredientService;

    @BeforeEach
    void setup() {
        ingredientRepository = mock(IngredientRepository.class);
        ingredientService = new IngredientService(ingredientRepository);
    }
    /** ------------------------------------------------------------------------
     *  ingredientService.getAllIngredients
     *  ------------------------------------------------------------------------ **/
    @Test
    void getAllIngredients_returns2ItemList() {
        //GIVEN
        List<IngredientRecord> records = new ArrayList<>();
        records.add(getIngredientRecord("Milk"));
        records.add(getIngredientRecord("Egg"));


        //WHEN
        when(ingredientRepository.findAll()).thenReturn(records);
        List<Ingredient> ingredients = ingredientService.getAllIngredients();

        //THEN
        assertNotNull(ingredients);
        assertEquals(records.size(), ingredients.size());
        assertEquals(records.get(0).getId(), ingredients.get(0).getId());
    }

    @Test
    void getAllIngredients_returnsEmptyItemList() {
        //GIVEN
        List<IngredientRecord> records = new ArrayList<>();

        //WHEN
        when(ingredientRepository.findAll()).thenReturn(records);
        List<Ingredient> ingredients = ingredientService.getAllIngredients();

        //THEN
        assertNotNull(ingredients);
        assertEquals(records.size(), ingredients.size());
    }

    /** ------------------------------------------------------------------------
     *  ingredientService.getOrCreateIngredient
     *  ------------------------------------------------------------------------ **/
    @Test
    void getOrCreateIngredient_returnsFoundIngredient() {
        //GIVEN
        List<IngredientRecord> ingredientRecordList = new ArrayList<>();
        IngredientRecord record = getIngredientRecord("Milk");
        ingredientRecordList.add(record);

        Ingredient ingredient = new Ingredient(record.getId(), record.getName());

        //WHEN
        when(ingredientRepository.findAll()).thenReturn(ingredientRecordList);
        Ingredient actual = ingredientService.getOrCreateIngredient(record.getName());

        //THEN
        assertNotNull(actual);
        assertEquals(ingredient.getId(), actual.getId());
    }

    @Test
    void getOrCreateIngredient_returnsNewlyCreatedIngredient() {
        //GIVEN
        List<IngredientRecord> ingredientRecordList = new ArrayList<>();
        IngredientRecord record = getIngredientRecord("Milk");
        ingredientRecordList.add(record);


        Ingredient ingredient = new Ingredient(record.getId(), record.getName());

        //WHEN
        when(ingredientRepository.findAll()).thenReturn(ingredientRecordList);
        Ingredient actual = ingredientService.getOrCreateIngredient("Egg");

        //THEN
        assertNotNull(actual);
        assertEquals("Egg", actual.getName());
    }

    /** ------------------------------------------------------------------------
     *  ingredientService.createIngredient
     *  ------------------------------------------------------------------------ **/
    @Test
    void createIngredient_returnsIngredient() {
        //GIVEN
        IngredientCreateRequest request = new IngredientCreateRequest();
        request.setName("Milk");
        IngredientRecord record = getIngredientRecord("Milk");

        Ingredient ingredient = new Ingredient(record.getId(), record.getName());

        //WHEN
        when(ingredientRepository.save(record)).thenReturn(record);
        Ingredient actual = ingredientService.createIngredient(request);

        //THEN
        assertNotNull(actual);
        assertEquals(ingredient.getName(), actual.getName());
    }
    private IngredientRecord getIngredientRecord(String name){
        IngredientRecord record = new IngredientRecord();
        record.setId(randomUUID().toString());
        record.setName(name);
        return record;
    }
}
