package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter; // Asegúrate que esté importado
import lombok.Setter; // Asegúrate que esté importado
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_cita_tratamiento")
@Getter // Confirmado
@Setter // Confirmado
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCitaTratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cita_id")
    private Integer citaId; // Antes ManyToOne Cita

    @Column(name = "tratamiento_id")
    private Integer tratamientoId; // Antes ManyToOne Tratamiento

    @Column(name = "observacion", columnDefinition = "TEXT")
    private String observacion;
}