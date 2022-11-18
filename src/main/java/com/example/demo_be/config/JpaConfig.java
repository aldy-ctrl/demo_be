package com.example.demo_be.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = "com.example.demo_be.repository")
public class JpaConfig {

}
