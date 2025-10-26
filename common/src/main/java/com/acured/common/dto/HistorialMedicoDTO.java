package com.acured.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HistorialMedicoDTO {

    private Integer id;

    @NotNull(message = "El ID del paciente es obligatorio")
    private Integer pacienteId;

    @Size(max = 5000, message = "La descripción no puede exceder los 5000 caracteres")
    private String descripcion;

    // fechaRegistro is usually set by the backend
    private LocalDateTime fechaRegistro; // For responses

    @Size(max = 1000, message = "La URL del archivo no puede exceder los 1000 caracteres")
    private String urlArchivo;
}