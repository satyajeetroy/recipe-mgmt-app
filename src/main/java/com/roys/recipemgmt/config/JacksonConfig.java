package com.roys.recipemgmt.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure Jackson for Recipe application
 * 
 * @author Satyajeet Roy
 *
 */
@Configuration
public class JacksonConfig {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
		return jacksonObjectMapperBuilder -> {
			jacksonObjectMapperBuilder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					DeserializationFeature.ACCEPT_FLOAT_AS_INT, SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		};
	}

}