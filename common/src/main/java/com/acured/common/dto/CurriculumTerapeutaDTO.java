package com.acured.common.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CurriculumTerapeutaDTO {

    private Integer id;

    @NotNull(message = "El ID del terapeuta (usuario) es obligatorio")
    private Integer terapeutaId; // Asumiendo que es un ID de la tabla 'usuario'

    @NotBlank(message = "La institución es obligatoria")
    @Size(max = 200, message = "La institución no puede exceder los 200 caracteres")
    private String institucion;

    @NotBlank(message = "El cargo es obligatorio")
    @Size(max = 150, message = "El cargo no puede exceder los 150 caracteres")
    private String cargo;

    private String descripcion;

    private LocalDate fechaInicio;

    private LocalDate fechaFin; // Puede ser nulo si es el trabajo actual
}