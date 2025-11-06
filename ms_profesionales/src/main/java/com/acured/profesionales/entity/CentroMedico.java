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