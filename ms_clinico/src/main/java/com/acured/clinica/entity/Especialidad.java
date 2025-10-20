package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Table(name = "especialidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true, length = 150)
    private String nombre;
    
    // Relación inversa: Una especialidad puede tener muchos tratamientos
    @OneToMany(mappedBy = "especialidad", fetch = FetchType.LAZY)
    private Set<Tratamiento> tratamientos;
}