package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@IntegrationTest
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllRecipe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetRecipeById() throws Exception {

        Recipe recipe = recipeService.addNewRecipe(new RecipeCreateRequest());

        // Send a GET request to retrieve the recipe by its ID
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/{id}", recipe.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(recipe.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(recipe.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ingredients").value(recipe.getIngredientsAsString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeToPrepare").value(recipe.getTimeToPrepare()))
                .andDo(print());
    }

    @Test
    public void testAddNewRecipe() throws Exception {
        RecipeCreateRequest request = new RecipeCreateRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ingredients").value(request.getIngredients()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeToPrepare").value(request.getTimeToPrepare()))
                .andDo(print());
    }
}
