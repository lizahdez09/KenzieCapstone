package com.kenzie.capstone.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kenzie.capstone.service.caching.CacheClient;
import com.kenzie.capstone.service.dao.CachingUserDao;
import com.kenzie.capstone.service.dao.NonCachingUserDao;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.InvalidLogInCredentials;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private CacheClient cacheClient;
    private UserDao userDao;
    private NonCachingUserDao nonCachingUserDao;
    private UserService userService;

    private Gson GSON;

    @BeforeEach
    void setUp() {
        GSON = builder.create();
        cacheClient = mock(CacheClient.class);
        nonCachingUserDao = mock(NonCachingUserDao.class);
        userDao = new CachingUserDao(cacheClient, nonCachingUserDao);
        userService = new UserService(userDao);
    }

    @Test
    void testGetUserData_ExistingUser_ReturnsUserRecord_FromDB() {
        // Given
        UserRecord expected = createUserRecord_FromId_EmptyList("TestId");

        when(cacheClient.getValue(expected.getEmail())).thenReturn(Optional.empty());
        when(nonCachingUserDao.getUserByEmail(expected.getEmail())).thenReturn(expected);
        // When
        UserRecord actual = userService.getUserData(expected.getEmail());

        // Then
        assertEquals(expected, actual);
        verify(nonCachingUserDao, times(1)).getUserByEmail(expected.getEmail());
    }

    @Test
    void testGetUserData_ExistingUser_ReturnsUserRecord_FromCache() {
        // Given
        UserRecord expected = createUserRecord_FromId_EmptyList("TestId");

        when(cacheClient.getValue(expected.getEmail())).thenReturn(Optional.ofNullable(GSON.toJson(expected)));
        // When
        UserRecord actual = userService.getUserData(expected.getEmail());

        // Then
        assertEquals(expected, actual);
        verify(cacheClient, times(1)).getValue(expected.getEmail());
        verify(nonCachingUserDao, times(0)).getUserByEmail(expected.getEmail());
    }

    @Test
    void testGetUserData_ThrowsRunTimeException_nullFromDB() {
        //GIVEN
        String email = "test";
        //WHEN
        when(cacheClient.getValue(email)).thenReturn(Optional.empty());
        when(nonCachingUserDao.getUserByEmail(email)).thenReturn(null);

        //THEN
        assertThrows(InvalidLogInCredentials.class, () -> userService.getUserData(email));
        verify(cacheClient, times(1)).getValue(email);
        verify(nonCachingUserDao, times(1)).getUserByEmail(email);
    }

    @Test
    void testSetUserData_ValidRequest_ReturnsUserRecord() {
        // Given
        List<String> testFavorites = new ArrayList<>();
        testFavorites.add("testFavorite");

        UserRequest request = createUserRequest_FromId_WithList("1", testFavorites);

        UserRecord expected = new UserRecord();
        expected.setId(request.getId());
        expected.setEmail(request.getEmail());
        expected.setName(request.getName());
        expected.setPassword(request.getPassword());
        expected.setFavoriteRecipes(request.getFavoriteRecipes());

        when(nonCachingUserDao.createNewUser(request)).thenReturn(expected);

        // When
        UserRecord actualRecord = userService.setUserData(request);

        // Then
        assertEquals(expected, actualRecord);
        verify(nonCachingUserDao, times(1)).createNewUser(request);
    }

    @Test
    void testUpdateUserFavoriteRecipes_ExistingUser_UpdatesAndReturnsUser_FromDB() {
        //GIVEN
        String id = randomUUID().toString();
        List<String> favorites = List.of("TestFavorite");
        UserRecord oldRecord = createUserRecord_FromId_EmptyList(id);

        UserUpdateRequest request = new UserUpdateRequest();
        request.setEmail(oldRecord.getEmail());
        request.setFavoriteRecipes(favorites);

        UserRecord expected = oldRecord;
        expected.setFavoriteRecipes(favorites);

        //WHEN
        when(cacheClient.getValue(request.getEmail())).thenReturn(Optional.empty());
        when(nonCachingUserDao.getUserByEmail(request.getEmail())).thenReturn(oldRecord);
        when(nonCachingUserDao.updateUser(request, oldRecord)).thenReturn(expected);

        //THEN
        UserRecord actual = userService.updateUserFavoriteRecipes(request);

        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getFavoriteRecipes(), actual.getFavoriteRecipes());
    }

    @Test
    void testUpdateUserFavoriteRecipes_ExistingUser_UpdatesAndReturnsUser_FromCache() {
        //GIVEN
        String id = randomUUID().toString();
        List<String> favorites = List.of("TestFavorite");
        UserRecord oldRecord = createUserRecord_FromId_EmptyList(id);

        UserUpdateRequest request = new UserUpdateRequest();
        request.setEmail(oldRecord.getEmail());
        request.setFavoriteRecipes(favorites);

        UserRecord expected = oldRecord;
        expected.setFavoriteRecipes(favorites);
        String jsonUser = GSON.toJson(oldRecord);
        //WHEN
        when(cacheClient.getValue(request.getEmail())).thenReturn(Optional.ofNullable(jsonUser));
        when(nonCachingUserDao.updateUser(request, oldRecord)).thenReturn(expected);

        //THEN
        UserRecord actual = userService.updateUserFavoriteRecipes(request);

        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getFavoriteRecipes(), actual.getFavoriteRecipes());
    }

    /**
     * PRIVATE HELPER METHODS
     */
    GsonBuilder builder = new GsonBuilder().registerTypeAdapter(
            ZonedDateTime.class,
            new TypeAdapter<ZonedDateTime>() {
                @Override
                public void write(JsonWriter out, ZonedDateTime value) throws IOException {
                    out.value(value.toString());
                }
                @Override
                public ZonedDateTime read(JsonReader in) throws IOException {
                    return ZonedDateTime.parse(in.nextString());
                }
            }
    ).enableComplexMapKeySerialization();
    private UserRecord createUserRecord_FromId_EmptyList(String id){
        UserRecord userRecord = new UserRecord();
        userRecord.setId(id);
        userRecord.setEmail("TestEmail");
        userRecord.setName("TestName");
        userRecord.setPassword("TestPassword");
        userRecord.setFavoriteRecipes(new ArrayList<>());
        return userRecord;
    }
    private UserRecord createUserRecord_FromId_WithList(String id, List<String> favorites){
        UserRecord userRecord = new UserRecord();
        userRecord.setId(id);
        userRecord.setEmail("TestEmail");
        userRecord.setName("TestName");
        userRecord.setPassword("TestPassword");
        userRecord.setFavoriteRecipes(favorites);
        return userRecord;
    }

    private UserRequest createUserRequest_FromId_WithList(String id, List<String> favorites) {
        UserRequest request = new UserRequest();
        request.setId(id);
        request.setName("TestName");
        request.setPassword("TestPassword");
        request.setEmail("TestEmail");
        request.setFavoriteRecipes(favorites);
        return request;
    }
}