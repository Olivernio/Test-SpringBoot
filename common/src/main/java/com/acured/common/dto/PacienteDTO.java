package com.acured.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PacienteDTO {
    // Los datos que el cliente SÍ puede ver
    private Integer id;
    private Integer usuarioId;
    private LocalDate fechaNacimiento;
    private String genero;
    private String observaciones;
    
    // Nota: No incluimos el "Set<HistorialMedico>" ni "Set<Cita>"
    // para evitar los loops y la carga perezosa.
}