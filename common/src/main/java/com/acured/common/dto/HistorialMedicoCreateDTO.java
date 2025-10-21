package com.acured.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HistorialMedicoCreateDTO {
    private Integer pacienteId;
    private String descripcion;
    private String urlArchivo;
}