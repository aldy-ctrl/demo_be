package com.example.demo_be.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("com.example.demo_be.entity")
public class EntityConfig {

}
