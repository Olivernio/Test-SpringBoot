package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter 
@Setter 
@Entity
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) 
    private Integer id;

    // ID del usuario (de otro microservicio). No hacemos JOIN.
    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "genero", length = 20)
    private String genero;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}