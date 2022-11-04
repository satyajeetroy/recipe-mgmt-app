package com.roys.recipemgmt.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for enabling JPA and performing scan for Entity related
 * annotations
 * 
 * @author Satyajeet Roy
 *
 */
@Configuration
@EntityScan("com.roys.recipemgmt.entity")
@EnableJpaRepositories("com.roys.recipemgmt.jpa")
@EnableTransactionManagement
public class EntityConfig {
}
