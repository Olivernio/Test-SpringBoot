package com.acured.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CitaCreateDTO {
    private Integer pacienteId;
    private Integer terapeutaId;
    private Integer centroId;
    private LocalDateTime fecha;
    private String motivo;
}