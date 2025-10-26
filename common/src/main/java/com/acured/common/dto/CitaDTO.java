package com.acured.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CitaDTO {
    private Integer id;
    private Integer pacienteId; // Antes PacienteDTO
    private Integer terapeutaId;
    private Integer centroId; // Antes CentroMedicoDTO
    private LocalDateTime fecha;
    private String estado;
    private String motivo;
    // Ya no contiene Set<DetalleCitaTratamiento> ni Set<SesionTerapeutica>
}