package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter; 
import lombok.Setter; 

import java.time.LocalDateTime;

@Getter 
@Setter 
@Entity
@Table(name = "centro_medico")
public class CentroMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable= false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "direccion", length = 300)
    private String direccion;

    @Column(name = "telefono", length = 50)
    private String telefono;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "sitio_web", length = 250)
    private String sitioWeb;

    // Guardamos solo el ID, asumiendo que Pais es de otro microservicio/contexto
    @Column(name = "pais_id") 
    private Integer paisId; 
}