package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relación: Muchas citas son de un paciente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    // ID del terapeuta (Usuario de otro microservicio)
    @Column(name = "terapeuta_id")
    private Integer terapeutaId;
    
    // Relación: Muchas citas ocurren en un centro médico
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_id")
    private CentroMedico centroMedico;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;
    
    // Relaciones inversas
    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DetalleCitaTratamiento> detallesTratamiento;

    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SesionTerapeutica> sesiones;

    // Asigna el estado por defecto antes de guardar
    @PrePersist
    protected void onPrePersist() {
        if (this.estado == null) {
            this.estado = "pendiente";
        }
    }
}