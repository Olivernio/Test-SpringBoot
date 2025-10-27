package com.acured.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PacienteDTO {
    // Los DTOs de respuesta no llevan validaciones
    private Integer id;
    private Integer usuarioId;
    private LocalDate fechaNacimiento;
    private String genero;
    private String observaciones;
}