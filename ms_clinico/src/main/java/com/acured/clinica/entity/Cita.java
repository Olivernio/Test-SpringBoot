package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cita")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "paciente_id")
    private Integer pacienteId;

    @Column(name = "terapeuta_id")
    private Integer terapeutaId;

    @Column(name = "centro_id")
    private Integer centroId;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "estado", length = 50) // Assuming DB default handles 'pendiente'
    private String estado;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;
}