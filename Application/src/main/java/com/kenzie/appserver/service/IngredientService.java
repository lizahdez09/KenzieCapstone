package com.kenzie.appserver.service;

import com.kenzie.appserver.exceptions.IngredientNotFoundException;
import com.kenzie.appserver.repositories.IngredientRepository;
import com.kenzie.appserver.repositories.model.IngredientRecord;
import com.kenzie.appserver.service.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    /**
     * Checks to see if Ingredient exists by name, If no Ingredient exists by that name it will make a call to
     * {@link IngredientService#createIngredient(String)} passing in the name to return a new {@link Ingredient}
     * @param name the name of the Ingredient
     * @return {@link Ingredient}
     * @see IngredientService#createIngredient(String)
     */
    public Ingredient getOrCreateIngredient(String name) {
        List<Ingredient> ingredients = getAllIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equals(name)) {
                return ingredient;
            }
        }
        return createIngredient(name);
    }

    /**
     * Creates a new Ingredient using {@link UUID#randomUUID()} to create an id, and the provided name.<br> Returns the new {@link Ingredient}.
     * @param name the name of the Ingredient
     * @return {@link Ingredient}
     */
    public Ingredient createIngredient(String name) {
        IngredientRecord record = new IngredientRecord();
        record.setId(randomUUID().toString());
        record.setName(name);
        ingredientRepository.save(record);
        return new Ingredient(record);
    }
}
