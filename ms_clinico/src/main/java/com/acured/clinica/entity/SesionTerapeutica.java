package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sesion_terapeutica")
public class SesionTerapeutica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cita_id")
    private Integer citaId;

    @Column(name = "fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") // Assuming DB default
    private LocalDateTime fecha;

    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
}