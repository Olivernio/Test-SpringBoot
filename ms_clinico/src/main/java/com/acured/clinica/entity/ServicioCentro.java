package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter; 
import lombok.Setter; 
import java.math.BigDecimal;

import java.time.LocalDateTime;

@Getter 
@Setter 
@Entity
@Table(name = "servicio_centro")
public class ServicioCentro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    // Relación: Muchos servicios pertenecen a un centro
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_id")
    private CentroMedico centroMedico;

    // Relación: Muchos servicios están asociados a un tratamiento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tratamiento_id")
    private Tratamiento tratamiento;

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "precio", precision = 12, scale = 2)
    private BigDecimal precio;
    
    @Column(name = "duracion_min")
    private Integer duracionMin;
}