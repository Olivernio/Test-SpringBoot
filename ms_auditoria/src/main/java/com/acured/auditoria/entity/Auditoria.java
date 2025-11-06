package com.acured.auditoria.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "tabla", length = 100)
    private String tabla;

    @Column(name = "operacion", length = 50)
    private String operacion;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "fecha") 
    private LocalDateTime fecha;

}