package com.acured.auditoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
// import org.springframework.cloud.openfeign.EnableFeignClients; // Esto es para Feign

@SpringBootApplication
@EnableDiscoveryClient // <-- Para registrarse en Eureka
@ComponentScan(basePackages = { // <-- LA SOLUCIÓN para tu error 404
    "com.acured.auditoria",      // El paquete de este microservicio
    "com.acured.common"        // El paquete 'common' (para encontrar el GlobalExceptionHandler)
})

public class AuditoriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditoriaApplication.class, args);
    }

}