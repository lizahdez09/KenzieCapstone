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


import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.when;

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
            String name = "John Doe";
            String email = "email";
            String password = "password";
           // String recipes = "Recipe1, Recipe2";String name = "John Doe";
            UserRecord userRecord = new UserRecord();
            userRecord.setId(id);
            userRecord.setName(name);
            userRecord.setEmail(email);
            userRecord.setPassword(password);
            when(userDao.getUserById(id)).thenReturn(userRecord);

            // Act
            User user = userService.getUserData(id);

            // Assert
            Assertions.assertEquals(id, user.getId());
//            Assertions.assertEquals(name, user.getName());
//            Assertions.assertEquals(email,user.getEmail());
//            Assertions.assertEquals(password,user.getPassword());
            //** have to check on this **//
        }

        @Test
        public void testGetUserData_nonExistingUser() {
            // Arrange
            String id = "123";
            when(userDao.getUserById(id)).thenReturn(null);

            // Act & Assert
            Assertions.assertThrows(RuntimeException.class, () -> userService.getUserData(id));
        }

    @Test
    public void testSetUserData() {
        // Arrange
        String id = randomUUID().toString();
        String name = "John Doe";
        String password = "password";
        String email = "email";

        UserRecord userRecord = new UserRecord();
        userRecord.setId(id);
        userRecord.setName(name);
        userRecord.setPassword(password);
        userRecord.setEmail(email);

        when(userDao.createNewUser(Mockito.anyString(), name, password, email)).thenReturn(userRecord);

        // Act
        User user = userService.setUserData(name, password, email);

        // Assert
        Assertions.assertEquals(id, user.getId());
        Assertions.assertEquals(name, user.getName());
        Assertions.assertEquals(password, user.getPassword());
        Assertions.assertEquals(email, user.getEmail());
    }

        @Test
        public void testUpdateUserData_existingUser() {
            // Arrange
            String id = "123";
            String email = "email";
            String name = "John Doe";
            String password = "password";
            UserRecord userRecord = new UserRecord();
            when(userDao.getUserById(id)).thenReturn(userRecord);

            // Act
            User user = userService.updateUserData(id,name,password,email);

            // Assert
            Assertions.assertEquals(id, user.getId());
            //Assertions.assertEquals(Arrays.asList("Recipe1", "Recipe2"), user.getRecipeId());
            Assertions.assertEquals(name, user.getName());

            Mockito.verify(userDao).updateUser(userRecord);
        }

        @Test
        public void testUpdateUserData_nonExistingUser() {
            // Arrange
            String id = "123";
            when(userDao.getUserById(id)).thenReturn(null);

            // Act & Assert
            Assertions.assertThrows(NotFoundException.class, () -> userService.updateUserData(id, "", "",""));

    }

}
/////////***** have to redo tests to reflect changes made in how data is created for users