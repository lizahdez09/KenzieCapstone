package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.IngredientCreateRequest;
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
     * {@link IngredientService#createIngredient(IngredientCreateRequest)} passing in the name to return a new {@link Ingredient}
     * @param name the name of the Ingredient
     * @return {@link Ingredient}
     * @see IngredientService#createIngredient(IngredientCreateRequest)
     */
    public Ingredient getOrCreateIngredient(String name) {
        List<Ingredient> ingredients = getAllIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        IngredientCreateRequest request = new IngredientCreateRequest();
        request.setName(name);
        return createIngredient(request);
    }

    /**
     * Creates a new Ingredient using {@link UUID#randomUUID()} to create an id,<br> and the provided name from the {@link IngredientCreateRequest}.<br> Returns the new {@link Ingredient}.
     * @param request the {@link IngredientCreateRequest}
     * @return {@link Ingredient}
     */
    public Ingredient createIngredient(IngredientCreateRequest request) {
        IngredientRecord record = new IngredientRecord();
        record.setId(randomUUID().toString());
        record.setName(request.getName());
        ingredientRepository.save(record);
        return new Ingredient(record);
    }
}
