package com.kenzie.appserver.service;

import com.kenzie.appserver.exceptions.IngredientNotFoundException;
import com.kenzie.appserver.repositories.IngredientRepository;
import com.kenzie.appserver.repositories.model.IngredientRecord;
import com.kenzie.appserver.service.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@Service
public class IngredientService {

    private IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();

        Iterable<IngredientRecord> recordIterable = ingredientRepository.findAll();
        for (IngredientRecord record : recordIterable) {
            ingredients.add(new Ingredient(record));
        }
        return ingredients;
    }

    public Ingredient getByName(String name) {
        List<Ingredient> ingredients = getAllIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equals(name)) {
                return ingredient;
            }
        }
        return createIngredient(name);
    }

    public Ingredient createIngredient(String name) {
        IngredientRecord record = new IngredientRecord();
        record.setId(randomUUID().toString());
        record.setName(name);
        ingredientRepository.save(record);
        return new Ingredient(record);
    }
}
