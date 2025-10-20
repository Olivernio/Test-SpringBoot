package com.acured.clinica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "tratamiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "duracion_min")
    private Integer duracionMin;

    @Column(name = "precio", precision = 12, scale = 2)
    private BigDecimal precio;

    // Relación: Muchos tratamientos pertenecen a una especialidad
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;
    
    // Relaciones inversas
    @OneToMany(mappedBy = "tratamiento", fetch = FetchType.LAZY)
    private Set<DetalleCitaTratamiento> detallesCita;

    @OneToMany(mappedBy = "tratamiento", fetch = FetchType.LAZY)
    private Set<ServicioCentro> serviciosCentro;
}