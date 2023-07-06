package com.kenzie.capstone.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userDao);
    }

    @Test
    void testGetUserData_ExistingUser_ReturnsUserRecord() {
        // Given
        String email = "test@example.com";
        UserRecord expectedRecord = new UserRecord();
        expectedRecord.setEmail(email);

        when(userDao.getUserByEmail(email)).thenReturn(expectedRecord);

        // When
        UserRecord actualRecord = userService.getUserData(email);

        // Then
        assertEquals(expectedRecord, actualRecord);
        verify(userDao, times(1)).getUserByEmail(email);
    }

    @Test
    void testGetUserData_NonExistingUser_ThrowsRuntimeException() {
        // Given
        String email = "nonexisting@example.com";

        when(userDao.getUserByEmail(email)).thenReturn(null);

        // When & Then
        assertThrows(RuntimeException.class, () -> userService.getUserData(email));
        verify(userDao, times(1)).getUserByEmail(email);
    }

    @Test
    void testSetUserData_ValidRequest_ReturnsUserRecord() {
        // Given
        UserRequest request = new UserRequest();
        UserRecord expectedRecord = new UserRecord();

        when(userDao.createNewUser(request)).thenReturn(expectedRecord);

        // When
        UserRecord actualRecord = userService.setUserData(request);

        // Then
        assertEquals(expectedRecord, actualRecord);
        verify(userDao, times(1)).createNewUser(request);
    }

//    @Test
//    void testUpdateUserFavoriteRecipes_ExistingUser_UpdatesAndReturnsUser() {
//        // Given
//        String email = "test@example.com";
//        List<String> favorites = new ArrayList<>();
//        favorites.add("Recipe 1");
//        favorites.add("Recipe 2");
//
//        UserRecord user = new UserRecord();
//        user.setEmail(email);
//
//        when(userDao.getUserByEmail(email)).thenReturn(user);
//
//        // Then
//        User actualUser = userService.updateUserFavoriteRecipes(email, favorites);
//
//        // When
//        assertEquals(email, actualUser.getEmail());
//        assertEquals(favorites, actualUser.getFavoriteRecipes());
//        verify(userDao, times(1)).getUserByEmail(email);
//        verify(userDao, times(1)).updateUser(user);
//    }

    @Test
    void testUpdateUserFavoriteRecipes_NonExistingUser_ThrowsNotFoundException() {
        // Given
        String email = "nonexisting@example.com";
        List<String> favorites = new ArrayList<>();
        favorites.add("Recipe 1");

        when(userDao.getUserByEmail(email)).thenReturn(null);

        // When & Then
        //TODO assertThrows(NotFoundException.class, () -> userService.updateUserFavoriteRecipes(email, favorites));
        verify(userDao, times(1)).getUserByEmail(email);
        verify(userDao, never()).updateUser(any());
    }
}