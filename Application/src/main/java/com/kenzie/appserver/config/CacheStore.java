package com.kenzie.appserver.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kenzie.appserver.service.model.Recipe;

import java.util.concurrent.TimeUnit;

public class CacheStore {

    private Cache<String, Recipe> mostFavoriteRecipecache;

    public CacheStore(int expiry, TimeUnit timeUnit) {
        this.mostFavoriteRecipecache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiry, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public Recipe get(String key) {
        return mostFavoriteRecipecache.getIfPresent(key);
    }

    public void evict(String key) {
        mostFavoriteRecipecache.invalidate(key);
    }

    public void add(String key, Recipe value) {
        mostFavoriteRecipecache.put(key, value);
    }
}
