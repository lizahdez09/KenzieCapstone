package com.kenzie.capstone.service.dependency;


import com.kenzie.capstone.service.caching.CacheClient;
import com.kenzie.capstone.service.dao.CachingUserDao;
import com.kenzie.capstone.service.dao.NonCachingUserDao;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.util.DynamoDbClientProvider;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Provides DynamoDBMapper instance to DAO classes.
 */
@Module(
        includes = CachingModule.class
)
public class DaoModule {

    @Singleton
    @Provides
    @Named("DynamoDBMapper")
    public DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
    }

    @Singleton
    @Provides
    @Named("UserDao")
    @Inject
    public UserDao provideCachingUserDao(
            @Named("CacheClient") CacheClient cacheClient,
            @Named("NonCachingUserDao") NonCachingUserDao nonCachingUserDao) {
        return new CachingUserDao(cacheClient, nonCachingUserDao);
    }

    @Singleton
    @Provides
    @Named("NonCachingUserDao")
    @Inject
    public NonCachingUserDao provideNonCachingUserDao(@Named("DynamoDBMapper") DynamoDBMapper mapper) {
        return new NonCachingUserDao(mapper);
    }
}
