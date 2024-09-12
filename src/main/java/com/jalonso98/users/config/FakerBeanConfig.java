package com.jalonso98.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;

@Configuration
public class FakerBeanConfig {

	@Bean
	public Faker getFaker() {
		return new Faker();
	}
	
}
