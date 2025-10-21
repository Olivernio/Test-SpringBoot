package com.acured.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TratamientoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer duracionMin;
    private BigDecimal precio;
    private EspecialidadDTO especialidad; // Usamos el DTO anidado
}