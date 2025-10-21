package com.acured.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TratamientoCreateDTO {
    private String nombre;
    private String descripcion;
    private Integer duracionMin;
    private BigDecimal precio;
    private Integer especialidadId; // Solo el ID para crear/actualizar
}