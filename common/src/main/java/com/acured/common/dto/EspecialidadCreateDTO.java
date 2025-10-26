package com.acured.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EspecialidadCreateDTO {

    // Nombre es NOT NULL, UNIQUE, VARCHAR(150)
    @NotBlank(message = "El nombre de la especialidad es obligatorio.")
    @Size(max = 150, message = "El nombre no puede exceder los 150 caracteres.")
    private String nombre;
}