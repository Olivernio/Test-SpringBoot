package com.acured.common.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CitaCreateDTO {

    // Todas las FK son obligatorias según el diseño lógico (una cita necesita paciente, terapeuta, centro)
    @NotNull(message = "El ID del paciente es obligatorio.")
    private Integer pacienteId;

    @NotNull(message = "El ID del terapeuta es obligatorio.")
    private Integer terapeutaId;

    @NotNull(message = "El ID del centro médico es obligatorio.")
    private Integer centroId;

    // Fecha es NOT NULL según SQL
    @NotNull(message = "La fecha de la cita es obligatoria.")
    @FutureOrPresent(message = "La fecha de la cita no puede ser pasada.")
    private LocalDateTime fecha;

    // Motivo es TEXT
    @Size(max = 10000, message = "El motivo es demasiado largo.") // Límite arbitrario
    private String motivo;

    // El 'estado' no se envía al crear, se asigna 'pendiente' por defecto (@PrePersist o BD)
}