package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.config.UserServiceClientConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import java.util.ArrayList;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private UserServiceClientConfiguration userService;
//    @Autowired
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Test
//    public void testGetUser_Success() throws Exception {
//        // Prepare test data
//        UserLoginResponse userResponse = new UserLoginResponse();
//        userResponse.setEmail("test@example.com");
//        userResponse.setPassword("password");
//
//        // Mock the UserServiceClient's behavior
//        String email = "test@example.com";
//        Mockito.when(userService.getUserData(email)).thenReturn(userResponse);
//
//        // Perform the GET request
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
//                .post("/user/login")
//                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
//                .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"));
//
//        // Verify the response
//        resultActions.andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
//                .andExpect((ResultMatcher) jsonPath("$.email").value("test@example.com"))
//                .andExpect((ResultMatcher) jsonPath("$.password").value("password"));
//    }
//
//    @Test
//    public void testGetUser_UserNotFound() throws Exception {
//        // Mock the UserServiceClient's behavior
//        String email = "test@example.com";
//        Mockito.when(userService.getUserData(email)).thenReturn(null);
//
//        // Perform the GET request
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
//                .post("/user/login")
//                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
//                .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"));
//
//        // Verify the response
//        resultActions.andExpect(status().isNoContent());
//    }
//
//    @Test
//    public void testGetUser_IncorrectPassword() throws Exception {
//        // Prepare test data
//        UserResponse userResponse = new UserResponse();
//        userResponse.setEmail("test@example.com");
//        userResponse.setPassword("password123");
//
//        // Mock the UserServiceClient's behavior
//        String email = "test@example.com";
//        Mockito.when(userService.getUserData(email)).thenReturn(userResponse);
//
//        // Perform the GET request
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
//                .post("/user/login")
//                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
//                .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"));
//
//        // Verify the response
//        resultActions.andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void testAddNewUser_Success() throws Exception {
//        // Prepare test data
//        UserResponse userResponse = new UserResponse();
//        userResponse.setEmail("test@example.com");
//
//        // Mock the UserServiceClient's behavior
//        UserRequest userRequest = new UserRequest();
//        userRequest.setEmail("test@example.com");
//        userRequest.setPassword("password");
//
//        Mockito.when(userService.setUserData(userRequest)).thenReturn(userResponse);
//
//        // Perform the POST request
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
//                .post("/user")
//                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
//                .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"));
//
//        // Verify the response
//        resultActions.andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
//                .andExpect((ResultMatcher) jsonPath("$.email").value("test@example.com"));
//    }
//
//    @Test
//    public void testUpdateUserFavoriteRecipes_Success() throws Exception {
//        // Prepare test data
//        UserResponse updatedUser = new UserResponse();
//        updatedUser.setEmail("test@example.com");
//        updatedUser.setFavoriteRecipes(new ArrayList<>());
//
//        // Mock the UserServiceClient's behavior
//        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
//        userUpdateRequest.setEmail("test@example.com");
//        userUpdateRequest.setFavoriteRecipes(new ArrayList<>());
//
//        Mockito.when(userService.updateUserFavoriteRecipes(userUpdateRequest)).thenReturn(updatedUser);
//
//        // Perform the POST request
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
//                .post("/user/update")
//                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
//                .content("{\"email\":\"test@example.com\",\"favoriteRecipes\":[]}"));
//
//        // Verify the response
//        resultActions.andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
//                .andExpect((ResultMatcher) jsonPath("$.email").value("test@example.com"))
//                .andExpect((ResultMatcher) jsonPath("$.favoriteRecipes").isArray());
//    }
}
