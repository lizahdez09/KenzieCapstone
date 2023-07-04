package com.kenzie.capstone.service.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kenzie.capstone.service.caching.CacheClient;
import com.kenzie.capstone.service.model.UserRecord;
import com.kenzie.capstone.service.model.UserRequest;

import javax.inject.Inject;
import java.io.IOException;
import java.time.ZonedDateTime;

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
        System.out.println("CachingDAO email - " + email);
        if (cacheClient.getValue(email).isPresent()) {
            System.out.println("User found in cache - " + cacheClient.getValue(email));
            return fromJson(String.valueOf(cacheClient.getValue(email)));
        } else {
            UserRecord user = nonCachingUserDao.getUserByEmail(email);
            System.out.println("User found from Dao - " + GSON.toJson(user));
            addToCache(user);
            return user;
        }
    }

    @Override
    public UserRecord updateUser(UserRecord record) {
        return null;
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
        return GSON.fromJson(json, UserRecord.class);
    }

    private void addToCache(UserRecord user) {
        cacheClient.setValue(user.getEmail(), hourTimer, GSON.toJson(user));
    }
}
