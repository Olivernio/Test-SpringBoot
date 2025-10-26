package com.acured.common.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaDTO {

    private Integer id; // For responses

    @NotNull(message = "El ID del paciente es obligatorio")
    private Integer pacienteId;

    @NotNull(message = "El ID del terapeuta es obligatorio")
    private Integer terapeutaId;

    @NotNull(message = "El ID del centro médico es obligatorio")
    private Integer centroId;

    @NotNull(message = "La fecha de la cita es obligatoria")
    @FutureOrPresent(message = "La fecha de la cita debe ser presente o futura")
    private LocalDateTime fecha;

    // Estado is usually set by backend logic or fetched, not sent on create/update
    @Size(max = 50, message = "El estado no puede exceder los 50 caracteres")
    private String estado; // For responses, maybe updates

    @Size(max = 5000, message = "El motivo no puede exceder los 5000 caracteres")
    private String motivo;
}