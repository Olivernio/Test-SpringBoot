package com.acured.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CitaDTO {
    private Integer id;
    private PacienteDTO paciente; // Usamos PacienteDTO
    private Integer terapeutaId;
    private CentroMedicoDTO centroMedico; // Usamos CentroMedicoDTO
    private LocalDateTime fecha;
    private String estado;
    private String motivo;
    // Omitimos 'detallesTratamiento' y 'sesiones' para una respuesta limpia
}