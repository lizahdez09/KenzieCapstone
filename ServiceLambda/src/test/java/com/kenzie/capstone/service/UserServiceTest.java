package com.kenzie.capstone.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.UUID;

public class UserServiceTest {
        @Mock
        private UserDao userDao;

        private UserService userService;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.initMocks(this);
            userService = new UserService(userDao);
        }

        @Test
        public void testGetUserData_existingUser() {
            // Arrange
            String id = "123";
            String recipes = "Recipe1, Recipe2";
            String name = "John Doe";
            UserRecord userRecord = new UserRecord(id, recipes, name);
            Mockito.when(userDao.getUserData(id)).thenReturn(userRecord);

            // Act
            User user = userService.getUserData(id);

            // Assert
            Assertions.assertEquals(id, user.getUserId());
            Assertions.assertEquals(Arrays.asList("Recipe1", "Recipe2"), user.getRecipeId());
            Assertions.assertEquals(name, user.getName());
        }

        @Test
        public void testGetUserData_nonExistingUser() {
            // Arrange
            String id = "123";
            Mockito.when(userDao.getUserData(id)).thenReturn(null);

            // Act & Assert
            Assertions.assertThrows(RuntimeException.class, () -> userService.getUserData(id));
        }

        @Test
        public void testSetUserData() {
            // Arrange
            String recipes = "Recipe1, Recipe2";
            String name = "John Doe";
            String generatedId = UUID.randomUUID().toString();
            UserRecord userRecord = new UserRecord(generatedId, recipes, name);
            Mockito.when(userDao.setUserData(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(userRecord);

            // Act
            User user = userService.setUserData(recipes, name);

            // Assert
            Assertions.assertEquals(generatedId, user.getUserId());
            Assertions.assertEquals(Arrays.asList("Recipe1", "Recipe2"), user.getRecipeId());
            Assertions.assertEquals(name, user.getName());
        }

        @Test
        public void testUpdateUserData_existingUser() {
            // Arrange
            String id = "123";
            String recipes = "Recipe1, Recipe2";
            String name = "John Doe";
            UserRecord userRecord = new UserRecord(id, recipes, name);
            Mockito.when(userDao.getUserData(id)).thenReturn(userRecord);

            // Act
            User user = userService.updateUserData(id, recipes, name);

            // Assert
            Assertions.assertEquals(id, user.getUserId());
            Assertions.assertEquals(Arrays.asList("Recipe1", "Recipe2"), user.getRecipeId());
            Assertions.assertEquals(name, user.getName());

            Mockito.verify(userDao).updateUserData(userRecord);
        }

        @Test
        public void testUpdateUserData_nonExistingUser() {
            // Arrange
            String id = "123";
            Mockito.when(userDao.getUserData(id)).thenReturn(null);

            // Act & Assert
            Assertions.assertThrows(NotFoundException.class, () -> userService.updateUserData(id, "", ""));

    }

}
