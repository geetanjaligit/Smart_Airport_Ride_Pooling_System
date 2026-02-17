package com.airport.ridepooling;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RidePoolingApplication {

    public static void main(String[] args) {
        // Explicitly load .env file and set variables as system properties
        // ensures Spring Boot can resolve ${VAR_NAME} placeholders
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(RidePoolingApplication.class, args);
    }

}
