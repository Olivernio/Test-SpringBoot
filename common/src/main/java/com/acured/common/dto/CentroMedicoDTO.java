package com.acured.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CentroMedicoDTO {
    private Integer id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String sitioWeb;
    private Integer paisId;
    // Ya no contiene Set<Cita> ni Set<ServicioCentro>
}