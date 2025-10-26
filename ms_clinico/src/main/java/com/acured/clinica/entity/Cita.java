package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter; // Asegúrate que esté importado
import lombok.Setter; // Asegúrate que esté importado
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
@Getter // Confirmado
@Setter // Confirmado
@NoArgsConstructor
@AllArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "paciente_id")
    private Integer pacienteId; // Antes ManyToOne Paciente

    @Column(name = "terapeuta_id")
    private Integer terapeutaId; // Se mantiene como ID

    @Column(name = "centro_id")
    private Integer centroId; // Antes ManyToOne CentroMedico

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    // --- Relaciones OneToMany eliminadas ---

    @PrePersist
    protected void onPrePersist() {
        if (this.estado == null) {
            this.estado = "pendiente";
        }
    }
}