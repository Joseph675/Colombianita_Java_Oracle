package com.colombianita.Colombianita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ColombianitaApplication {

	public static void main(String[] args) {
		// Debe ir antes de SpringApplication.run para que el pool JDBC herede el timezone correcto
		TimeZone.setDefault(TimeZone.getTimeZone("America/Bogota"));
		SpringApplication.run(ColombianitaApplication.class, args);
	}

}
