package com.kenzie.appserver.config;

import com.kenzie.capstone.service.client.UserServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceClientConfiguration {

    @Bean
    public UserServiceClient userServiceClient() {
        return new UserServiceClient();
    }
}
