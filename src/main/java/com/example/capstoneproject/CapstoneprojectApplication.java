package com.example.capstoneproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CapstoneprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapstoneprojectApplication.class, args);
    }

}
