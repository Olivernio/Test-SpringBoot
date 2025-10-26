package com.acured.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HistorialMedicoCreateDTO {

    // Un historial pertenece a un paciente
    @NotNull(message = "El ID del paciente es obligatorio.")
    private Integer pacienteId;

    // Descripción es TEXT
    @Size(max = 10000, message = "La descripción es demasiado larga.") // Límite arbitrario
    private String descripcion;

    // URL del archivo es TEXT
    @Size(max = 1024, message = "La URL del archivo es demasiado larga.") // Límite razonable para URL
    private String urlArchivo;

    // La fecha_registro se asigna automáticamente
}