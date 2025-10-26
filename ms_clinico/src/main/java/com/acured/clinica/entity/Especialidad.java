package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter; // Asegúrate que esté importado
import lombok.Setter; // Asegúrate que esté importado
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "especialidad")
@Getter // Confirmado
@Setter // Confirmado
@NoArgsConstructor
@AllArgsConstructor
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true, length = 150)
    private String nombre;

    // --- Relación OneToMany eliminada ---
}