package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "sesion_terapeutica")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SesionTerapeutica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relación: Muchas sesiones pueden pertenecer a una cita
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id")
    private Cita cita;
    
    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;
    
    // Asigna la fecha actual antes de guardar
    @PrePersist
    protected void onCreate() {
        if (this.fecha == null) {
            this.fecha = LocalDateTime.now();
        }
    }
}