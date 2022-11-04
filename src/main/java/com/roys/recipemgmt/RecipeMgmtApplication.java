package com.roys.recipemgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Recipe Management Application
 * 
 * @author Satyajeet Roy
 *
 */
@EnableWebMvc
@SpringBootApplication
public class RecipeMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeMgmtApplication.class, args);
	}

	@Bean
	public InternalResourceViewResolver defaultViewResolver() {
		return new InternalResourceViewResolver();
	}
}
