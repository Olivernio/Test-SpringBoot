package com.acured.common.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TratamientoCreateDTO {

    // Nombre es NOT NULL, VARCHAR(200)
    @NotBlank(message = "El nombre del tratamiento es obligatorio.")
    @Size(max = 200, message = "El nombre no puede exceder los 200 caracteres.")
    private String nombre;

    // Descripción es TEXT
    @Size(max = 10000, message = "La descripción es demasiado larga.") // Límite arbitrario
    private String descripcion;

    // Duración es INT
    @PositiveOrZero(message = "La duración debe ser un número positivo o cero.")
    private Integer duracionMin;

    // Precio es NUMERIC(12,2) y debería ser positivo
    @NotNull(message = "El precio es obligatorio.")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a cero.")
    @Digits(integer=10, fraction=2, message="El formato del precio no es válido (máximo 10 enteros y 2 decimales).") // 12-2 = 10
    private BigDecimal precio;

    // especialidad_id es INT y permite NULL según SQL, pero lógicamente debería ser obligatorio
    @NotNull(message = "El ID de la especialidad es obligatorio.")
    private Integer especialidadId;
}