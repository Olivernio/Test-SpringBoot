package com.acured.profesionales.entity; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity; // Asegúrate que esté importado
import jakarta.persistence.GeneratedValue; // Asegúrate que esté importado
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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