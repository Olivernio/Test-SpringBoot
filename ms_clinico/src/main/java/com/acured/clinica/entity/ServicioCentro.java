package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter; // Asegúrate que esté importado
import lombok.Setter; // Asegúrate que esté importado
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "servicio_centro")
@Getter // Confirmado
@Setter // Confirmado
@NoArgsConstructor
@AllArgsConstructor
public class ServicioCentro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "centro_id")
    private Integer centroId; // Antes ManyToOne CentroMedico

    @Column(name = "tratamiento_id")
    private Integer tratamientoId; // Antes ManyToOne Tratamiento

    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "precio", precision = 12, scale = 2)
    private BigDecimal precio;

    @Column(name = "duracion_min")
    private Integer duracionMin;
}