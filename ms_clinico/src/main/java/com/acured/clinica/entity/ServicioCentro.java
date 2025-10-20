package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "servicio_centro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioCentro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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