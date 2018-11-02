package com.lucas.geocoding.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.lucas.geocoding.service")
public class GeocodingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeocodingServiceApplication.class, args);
	}

}
