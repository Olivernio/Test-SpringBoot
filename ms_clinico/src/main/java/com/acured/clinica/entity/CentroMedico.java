package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter; // Asegúrate que esté importado
import lombok.Setter; // Asegúrate que esté importado
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "centro_medico")
@Getter // Confirmado
@Setter // Confirmado
@NoArgsConstructor
@AllArgsConstructor
public class CentroMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "direccion", length = 300)
    private String direccion;

    @Column(name = "telefono", length = 50)
    private String telefono;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "pais_id")
    private Integer paisId; // Se mantiene como ID

    @Column(name = "sitio_web", length = 250)
    private String sitioWeb;

    // --- Relaciones OneToMany eliminadas ---
}