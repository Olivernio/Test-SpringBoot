package com.acured.clinica.mapper;

import com.acured.common.dto.HistorialMedicoCreateDTO;
import com.acured.common.dto.HistorialMedicoDTO;
import com.acured.clinica.entity.HistorialMedico;
import com.acured.clinica.entity.Paciente;
import org.springframework.stereotype.Component;

@Component
public class HistorialMedicoMapper {

    public HistorialMedicoDTO toDTO(HistorialMedico historial) {
        if (historial == null) return null;
        HistorialMedicoDTO dto = new HistorialMedicoDTO();
        dto.setId(historial.getId());
        dto.setDescripcion(historial.getDescripcion());
        dto.setFechaRegistro(historial.getFechaRegistro());
        dto.setUrlArchivo(historial.getUrlArchivo());
        
        // Asigna el ID del paciente si no es nulo
        if (historial.getPaciente() != null) {
            dto.setPacienteId(historial.getPaciente().getId());
        }
        return dto;
    }

    public HistorialMedico toEntity(HistorialMedicoCreateDTO dto) {
        if (dto == null) return null;
        HistorialMedico historial = new HistorialMedico();
        historial.setDescripcion(dto.getDescripcion());
        historial.setUrlArchivo(dto.getUrlArchivo());
        // El Paciente se asignará en el Servicio
        // La fecha se asigna por @PrePersist
        return historial;
    }
}