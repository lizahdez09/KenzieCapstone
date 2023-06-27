package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.controller.model.RecipeUpdateRequest;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetAllRecipe() throws Exception {
        mockMvc.perform(get("/recipe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetRecipeById() throws Exception {
        RecipeCreateRequest request = createRecipeCreateRequest();

        Recipe recipe = recipeService.addNewRecipe(request);

        // Send a GET request to retrieve the recipe by its ID
        mockMvc.perform(get("/recipe/{id}", recipe.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(is(recipe.getId())))
                .andExpect(jsonPath("name").value(is(recipe.getName())))
                .andExpect(jsonPath("foodType").value(is(recipe.getFoodTypeAsString())))
                .andExpect(jsonPath("ingredients").value(is(recipe.getIngredientsAsString())))
                .andExpect(jsonPath("timeToPrepare").value(is(recipe.getTimeToPrepare())))
                .andDo(print());
    }

    @Test
    public void testAddNewRecipe() throws Exception {
        //GIVEN
        RecipeCreateRequest request = createRecipeCreateRequest();

        mapper.registerModule(new JavaTimeModule());
        //WHEN
        System.out.println(mapper.writeValueAsString(request));
        mockMvc.perform(post("/recipe")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
        //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(is(request.getName())))
                .andExpect(jsonPath("foodType").value(is(request.getFoodType())))
                .andExpect(jsonPath("timeToPrepare").value(is(request.getTimeToPrepare())))
                .andDo(print());
    }


    private String jsonIngredient(){
        return "[{\"id\":\"1\",\"name\":\"Ingredient\",\"amount\":\"1\",\"measurement\":\"TABLESPOON\"}]";
    }

    private RecipeCreateRequest createRecipeCreateRequest() {
        RecipeCreateRequest request = new RecipeCreateRequest();
        request.setName("RecipeTest");
        request.setFoodType("Lunch");
        request.setIngredients(jsonIngredient());
        request.setTimeToPrepare("30");
        return request;
    }

    private RecipeUpdateRequest createRecipeUpdateRequest() {
        RecipeUpdateRequest request = new RecipeUpdateRequest();
        request.setName("RecipeTest");
        request.setFoodType("Lunch");
        request.setIngredients(jsonIngredient());
        request.setTimeToPrepare("30");
        return request;
    }
}
