package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter; 
import lombok.Setter; 

import java.time.LocalDateTime;

@Getter 
@Setter 
@Entity
@Table(name = "especialidad")
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true, length = 150)
    private String nombre;
    
}