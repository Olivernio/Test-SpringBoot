package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ID del usuario (de otro microservicio). No hacemos JOIN.
    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "genero", length = 20)
    private String genero;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    // Relaciones inversas
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<HistorialMedico> historial;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    private Set<Cita> citas;
}