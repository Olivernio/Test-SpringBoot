package com.acured.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class HistorialMedicoDTO {
    private Integer id;
    private Integer pacienteId; // Solo el ID del paciente
    private String descripcion;
    private LocalDateTime fechaRegistro;
    private String urlArchivo;
}