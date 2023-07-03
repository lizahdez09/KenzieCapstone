package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.UserService;

import com.kenzie.capstone.service.dao.NonCachingUserDao;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Module(
    includes = DaoModule.class
)
public class ServiceModule {

    @Singleton
    @Provides
    @Inject
    public UserService provideLambdaService(@Named("UserDao") NonCachingUserDao nonCachingUserDao) {
        return new UserService(nonCachingUserDao);
    }
}

