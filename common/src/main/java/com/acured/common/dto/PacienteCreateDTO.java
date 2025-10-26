package com.acured.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PacienteCreateDTO {

    // Asumimos que el usuario_id viene de otro servicio y es obligatorio para crear un paciente
    @NotNull(message = "El ID de usuario es obligatorio.")
    private Integer usuarioId;

    // La fecha de nacimiento es opcional según el SQL (no tiene NOT NULL)
    @PastOrPresent(message = "La fecha de nacimiento no puede ser futura.")
    private LocalDate fechaNacimiento;

    // El género tiene un límite de 20 caracteres según el SQL
    @Size(max = 20, message = "El género no puede exceder los 20 caracteres.")
    private String genero;

    // Observaciones es de tipo TEXT, sin límite explícito, pero evitamos que sea excesivamente largo
    @Size(max = 10000, message = "Las observaciones son demasiado largas.") // Límite arbitrario, ajusta si es necesario
    private String observaciones;
}