package com.acured.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CentroMedicoDTO {

    private Integer id;

    @NotBlank(message = "El nombre del centro médico es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder los 200 caracteres")
    private String nombre;

    @Size(max = 300, message = "La dirección no puede exceder los 300 caracteres")
    private String direccion;

    @Size(max = 50, message = "El teléfono no puede exceder los 50 caracteres")
    private String telefono;

    @Email(message = "El formato del email no es válido")
    @Size(max = 150, message = "El email no puede exceder los 150 caracteres")
    private String email;

    @Size(max = 250, message = "El sitio web no puede exceder los 250 caracteres")
    private String sitioWeb;

    // Assuming Pais ID is mandatory
    @NotNull(message = "El ID del país es obligatorio")
    private Integer paisId;
}