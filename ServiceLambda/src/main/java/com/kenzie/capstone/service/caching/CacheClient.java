package com.kenzie.capstone.service.caching;

import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import redis.clients.jedis.Jedis;

import javax.inject.Inject;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import java.util.Optional;

public class CacheClient {

    @Inject
    public CacheClient(){
    }
    public void setValue(String key, int seconds, String value) {
        checkNonNullKey(key);
        Jedis cache = DaggerServiceComponent.create().provideJedis();
        try {
            cache.setex(key, seconds, value);
        } finally {
            cache.close();
        }
    }

    public Optional<String> getValue(String key) {
        checkNonNullKey(key);
        Jedis cache = DaggerServiceComponent.create().provideJedis();
        try {
            String value = cache.get(key);
            return Optional.ofNullable(value);
        } finally {
            cache.close();
        }
    }

    public void invalidate(String key) {
        checkNonNullKey(key);
        Jedis cache = DaggerServiceComponent.create().provideJedis();
        try {
            cache.del(key);
        } finally {
            cache.close();
        }
    }

    private void checkNonNullKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
    }

    @Module
    static class CacheModule {
        @Provides
        public Jedis provideJedis() {
            // Provide the Jedis instance here
            return new Jedis("localhost");
        }
    }

    @Component(modules = CacheModule.class)
    interface CacheComponent {
        Jedis provideJedis();
    }
}


