package com.acured.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PacienteCreateDTO {
    // Lo que el cliente necesita enviar para crear un paciente
    // No va el 'id', porque se genera solo.
    
    private Integer usuarioId; // Asumimos que el ID de usuario viene de otro microservicio
    private LocalDate fechaNacimiento;
    private String genero;
    private String observaciones;
}