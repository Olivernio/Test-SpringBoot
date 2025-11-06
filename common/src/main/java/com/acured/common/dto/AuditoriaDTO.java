package com.acured.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuditoriaDTO {

    private Integer id; // Para respuestas

    @NotBlank(message = "El nombre de la tabla es obligatorio")
    @Size(max = 100, message = "El nombre de la tabla no debe exceder los 100 caracteres")
    private String tabla;

    @NotBlank(message = "La operación es obligatoria")
    @Size(max = 50, message = "La operación no debe exceder los 50 caracteres")
    private String operacion;

    // El ID de usuario puede ser nulo (ej. operaciones del sistema)
    private Integer usuarioId;

    // La fecha se asignará automáticamente en el backend
    private LocalDateTime fecha;
}