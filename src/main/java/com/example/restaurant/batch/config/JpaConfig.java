package com.example.restaurant.batch.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.example.restaurant.repository")
@EntityScan(basePackages = "com.example.restaurant.entity")
@EnableJpaAuditing
@Configuration
public class JpaConfig {
}
