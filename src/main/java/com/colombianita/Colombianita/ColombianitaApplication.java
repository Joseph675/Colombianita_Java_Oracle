package com.colombianita.Colombianita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class ColombianitaApplication {

	@PostConstruct
	public void init() {
		// Establece la zona horaria por defecto para toda la aplicación (Hora de Colombia)
		TimeZone.setDefault(TimeZone.getTimeZone("America/Bogota"));
	}

	public static void main(String[] args) {
		SpringApplication.run(ColombianitaApplication.class, args);
	}

}
