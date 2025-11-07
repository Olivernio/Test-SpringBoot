package com.acured.clinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
    "com.acured.clinica",
    "com.acured.common"
})

public class ClinicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicaApplication.class, args);
    }
}