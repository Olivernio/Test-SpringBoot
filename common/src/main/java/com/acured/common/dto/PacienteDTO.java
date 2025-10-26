package com.acured.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PacienteDTO {

    private Integer id; // Null when creating, populated when reading

    @NotNull(message = "El ID de usuario no puede ser nulo")
    private Integer usuarioId;

    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El género no puede estar vacío")
    @Size(max = 20, message = "El género no puede exceder los 20 caracteres")
    private String genero;

    @Size(max = 5000, message = "Las observaciones no pueden exceder los 5000 caracteres")
    private String observaciones;
}