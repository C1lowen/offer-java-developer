package com.offer.testjava.config;

import com.offer.testjava.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class UserRepositoryConfig {
    @Bean
    public List<User> users() {
        return Collections.synchronizedList(new ArrayList<>());
    }
}
