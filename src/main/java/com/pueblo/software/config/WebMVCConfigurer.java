package com.pueblo.software.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMVCConfigurer implements WebMvcConfigurer{


	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/login")
		.allowedOrigins("http://localhost:8080")
		.allowedMethods("POST", "GET", "OPTIONS")
		.allowedHeaders("*")

		.allowCredentials(true).maxAge(3600);

		registry.addMapping("/api/homescreen")
		.allowedOrigins("http://localhost:8080")
		.allowedMethods("GET", "OPTIONS")
		.allowedHeaders("*")

		.allowCredentials(true).maxAge(3600);
		// Add more mappings...
	}

}
