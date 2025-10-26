package com.acured.common.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TratamientoDTO {

    private Integer id;

    @NotBlank(message = "El nombre del tratamiento es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder los 200 caracteres")
    private String nombre;

    @Size(max = 5000, message = "La descripción no puede exceder los 5000 caracteres")
    private String descripcion;

    @Min(value = 1, message = "La duración debe ser al menos 1 minuto")
    private Integer duracionMin;

    @NotNull(message = "El precio del tratamiento es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotNull(message = "El ID de la especialidad es obligatorio")
    private Integer especialidadId; // Store only the ID as per professor's style

}