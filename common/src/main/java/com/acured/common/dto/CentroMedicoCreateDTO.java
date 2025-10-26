package com.acured.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CentroMedicoCreateDTO {

    // Nombre es NOT NULL y VARCHAR(200) según el SQL
    @NotBlank(message = "El nombre del centro médico es obligatorio.")
    @Size(max = 200, message = "El nombre no puede exceder los 200 caracteres.")
    private String nombre;

    // Dirección es VARCHAR(300)
    @Size(max = 300, message = "La dirección no puede exceder los 300 caracteres.")
    private String direccion;

    // Teléfono es VARCHAR(50)
    @Size(max = 50, message = "El teléfono no puede exceder los 50 caracteres.")
    private String telefono;

    // Email es VARCHAR(150) y debería ser válido
    @Email(message = "Debe proporcionar un email válido.")
    @Size(max = 150, message = "El email no puede exceder los 150 caracteres.")
    private String email;

    // Sitio web es VARCHAR(250)
    @Size(max = 250, message = "El sitio web no puede exceder los 250 caracteres.")
    private String sitioWeb;

    // pais_id es INT y permite NULL según el SQL
    private Integer paisId; // Si fuera obligatorio, añadir @NotNull
}