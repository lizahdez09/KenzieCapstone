package com.kenzie.capstone.service.caching;

import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import redis.clients.jedis.Jedis;

import java.util.Optional;

    public class CacheClient {
        public void setValue(String key, int seconds, String value) {
            checkNonNullKey(key);
            try (Jedis cache = DaggerServiceComponent.create().provideJedis()) {
                cache.setex(key, seconds, value);
            }
        }

        public Optional<String> getValue(String key) {
            checkNonNullKey(key);
            try (Jedis cache = DaggerServiceComponent.create().provideJedis()) {
                String value = cache.get(key);
                return Optional.ofNullable(value);
            }
        }

        public void invalidate(String key) {
            checkNonNullKey(key);
            try (Jedis cache = DaggerServiceComponent.create().provideJedis()) {
                cache.del(key);
            }
        }

        private void checkNonNullKey(String key) {
            if (key == null) {
                throw new IllegalArgumentException("Key cannot be null");
            }
        }
    }


