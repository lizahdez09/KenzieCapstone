package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.kenzie.appserver.IntegrationTest;

import com.kenzie.appserver.controller.model.IngredientCreateRequest;
import com.kenzie.appserver.service.IngredientService;
import com.kenzie.appserver.service.model.Ingredient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@IntegrationTest
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetAllIngredients() throws Exception {
        mockMvc.perform(get("/ingredient")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetIngredientByName() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Milk");

        Ingredient ingredient1 = ingredientService.getOrCreateIngredient(ingredient.getName());

        mockMvc.perform(get("/ingredient/{name}", ingredient.getName())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(ingredient1.getId()))
                .andExpect(jsonPath("name").value(ingredient1.getName()));
    }

    @Test
    public void testAddNewIngredient() throws Exception {
        IngredientCreateRequest request = new IngredientCreateRequest();
        request.setName("Milk");


        mockMvc.perform(post("/ingredient")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").value(request.getName()));
    }
}
