package com.acured.clinica.mapper;

import com.acured.common.dto.PacienteCreateDTO;
import com.acured.common.dto.PacienteDTO;
import com.acured.clinica.entity.Paciente;
import org.springframework.stereotype.Component;

@Component // Le dice a Spring que gestione esta clase
public class PacienteMapper {

    // Convierte de Entidad -> DTO (para enviar respuestas)
    public PacienteDTO toDTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setUsuarioId(paciente.getUsuarioId());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        dto.setGenero(paciente.getGenero());
        dto.setObservaciones(paciente.getObservaciones());
        return dto;
    }

    // Convierte de DTO -> Entidad (para recibir peticiones)
    public Paciente toEntity(PacienteCreateDTO dto) {
        Paciente paciente = new Paciente();
        // Ojo: No ponemos el ID, se genera solo
        paciente.setUsuarioId(dto.getUsuarioId());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setGenero(dto.getGenero());
        paciente.setObservaciones(dto.getObservaciones());
        return paciente;
    }
}