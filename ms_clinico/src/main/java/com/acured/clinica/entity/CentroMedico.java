package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Table(name = "centro_medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CentroMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    // Relaciones inversas
    @OneToMany(mappedBy = "centroMedico", fetch = FetchType.LAZY)
    private Set<Cita> citas;

    @OneToMany(mappedBy = "centroMedico", fetch = FetchType.LAZY)
    private Set<ServicioCentro> serviciosCentro;
}