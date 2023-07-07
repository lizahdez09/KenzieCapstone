package com.kenzie.capstone.service.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kenzie.capstone.service.caching.CacheClient;
import com.kenzie.capstone.service.model.User;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserRequest;
import com.kenzie.capstone.service.model.UserUpdateRequest;

import javax.inject.Inject;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Optional;

public class CachingUserDao implements UserDao {

    private static final int hourTimer = 60 * 60;
    private final CacheClient cacheClient;
    private final NonCachingUserDao nonCachingUserDao;
    private final Gson GSON;

    @Inject
    public CachingUserDao(CacheClient cacheClient, NonCachingUserDao nonCachingUserDao) {
        this.cacheClient = cacheClient;
        this.nonCachingUserDao = nonCachingUserDao;
        this.GSON = builder.create();
    }

    @Override
    public UserRecord getUserByEmail(String email) {
        Optional<String> cachedValue = cacheClient.getValue(email);

        if (cachedValue.isPresent()) {
            String json = cachedValue.get();
            System.out.println("User found in cache - " + json);
            return fromJson(json);
        } else {
            UserRecord user = nonCachingUserDao.getUserByEmail(email);
            System.out.println("User found from Dao - " + GSON.toJson(user));
            addToCache(user);
            return user;
        }
    }

    @Override
    public UserRecord updateUser(UserUpdateRequest userUpdateRequest) {
        Optional<String> cachedValue = cacheClient.getValue(userUpdateRequest.getEmail());
        if (cachedValue.isPresent()) {
            String json = cachedValue.get();
            UserRecord userRecord = fromJson(json);
            nonCachingUserDao.updateUser(userUpdateRequest, userRecord);
            addToCache(userRecord);
            return userRecord;
        } else {
            UserRecord userRecord = nonCachingUserDao.getUserByEmail(userUpdateRequest.getEmail());
            nonCachingUserDao.updateUser(userUpdateRequest, userRecord);
            addToCache(userRecord);
            return userRecord;
        }
    }

    @Override
    public UserRecord createNewUser(UserRequest request) {
        UserRecord user = nonCachingUserDao.createNewUser(request);
        addToCache(user);
        return user;
    }

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

    private UserRecord fromJson(String json) {
        return GSON.fromJson(json, new TypeToken<UserRecord>(){}.getType());
    }

    private void addToCache(UserRecord user) {
        cacheClient.setValue(user.getEmail(), hourTimer, GSON.toJson(user));
    }
}
