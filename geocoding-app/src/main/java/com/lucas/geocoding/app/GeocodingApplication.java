package com.lucas.geocoding.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.lucas.geocoding.app")
public class GeocodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeocodingApplication.class, args);
	}

}
