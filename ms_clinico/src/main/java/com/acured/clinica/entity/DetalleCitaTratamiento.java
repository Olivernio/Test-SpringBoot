package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "detalle_cita_tratamiento")
public class DetalleCitaTratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cita_id")
    private Integer citaId;

    @Column(name = "tratamiento_id")
    private Integer tratamientoId;

    @Column(name = "observacion", columnDefinition = "TEXT")
    private String observacion;
}