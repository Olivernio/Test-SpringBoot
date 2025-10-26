package com.acured.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class HistorialMedicoDTO {
    private Integer id;
    private Integer pacienteId; // Antes PacienteDTO (implícito)
    private String descripcion;
    private LocalDateTime fechaRegistro;
    private String urlArchivo;
}