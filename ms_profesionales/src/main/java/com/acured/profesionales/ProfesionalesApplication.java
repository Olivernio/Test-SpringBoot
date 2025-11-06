package com.acured.profesionales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// @EnableDiscoveryClient le dice a Spring que este servicio
// debe registrarse en Eureka (aunque a veces es automático).
@EnableDiscoveryClient 
@SpringBootApplication
public class ProfesionalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfesionalesApplication.class, args);
    }

}