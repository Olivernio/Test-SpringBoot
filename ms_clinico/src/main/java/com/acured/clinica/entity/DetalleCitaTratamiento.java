package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_cita_tratamiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCitaTratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relación: Muchos detalles pertenecen a una cita
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id")
    private Cita cita;

    // Relación: Muchos detalles apuntan a un tratamiento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tratamiento_id")
    private Tratamiento tratamiento;
    
    @Column(name = "observacion", columnDefinition = "TEXT")
    private String observacion;
}