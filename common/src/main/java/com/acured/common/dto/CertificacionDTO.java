package com.acured.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CertificacionDTO {

    private Integer id;

    @NotBlank(message = "El nombre de la certificación es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder los 200 caracteres")
    private String nombre;

    @Size(max = 200, message = "La institución no puede exceder los 200 caracteres")
    private String institucion;

    private String descripcion;
}