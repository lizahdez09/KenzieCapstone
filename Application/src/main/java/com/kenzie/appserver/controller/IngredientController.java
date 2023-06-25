package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.IngredientCreateRequest;
import com.kenzie.appserver.controller.model.IngredientResponse;
import com.kenzie.appserver.service.IngredientService;
import com.kenzie.appserver.service.model.Ingredient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private IngredientService ingredientService;

    IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<IngredientResponse>> getAllIngredients() {
        List<IngredientResponse> responseList = new ArrayList<>();

        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        for (Ingredient ingredient : ingredients) {
            responseList.add(createResponseFromIngredient(ingredient));
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{name}")
    public ResponseEntity<IngredientResponse> getIngredientByName(@PathVariable("name") String name) {
        //ingredientService.getOrCreateIngredient will create the Ingredient if not found, so we can just return it
        return ResponseEntity.ok(createResponseFromIngredient(ingredientService
                .getOrCreateIngredient(name)));
    }

    @PostMapping
    public ResponseEntity<IngredientResponse> addNewIngredient(@RequestBody IngredientCreateRequest ingredientCreateRequest) {
        //ingredientService.getOrCreateIngredient checks if the ingredient exists by name before creating
        return ResponseEntity.ok(createResponseFromIngredient(ingredientService
                .getOrCreateIngredient(ingredientCreateRequest.getName())));
    }

    private IngredientResponse createResponseFromIngredient(Ingredient ingredient) {
        IngredientResponse response = new IngredientResponse();
        response.setId(ingredient.getId());
        response.setName(ingredient.getName());
        return response;
    }
}
