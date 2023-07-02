package com.kenzie.appserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    // Create a Cache here if needed

    @Bean
    public CacheStore MostFavoriteRecipeCache() {
        return new CacheStore(30, TimeUnit.MINUTES);
    }
}
//changes cache to store and expire every 30 mins instead of every 2 mins