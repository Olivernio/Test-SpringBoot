package com.acured.clinica.mapper;

import com.acured.common.dto.HistorialMedicoCreateDTO;
import com.acured.common.dto.HistorialMedicoDTO;
import com.acured.clinica.entity.HistorialMedico;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistorialMedicoMapper {

    // Mapea automáticamente HistorialMedico.pacienteId -> HistorialMedicoDTO.pacienteId
    HistorialMedicoDTO toDTO(HistorialMedico historial);

    // Mapea automáticamente HistorialMedicoCreateDTO.pacienteId -> HistorialMedico.pacienteId
    HistorialMedico toEntity(HistorialMedicoCreateDTO dto);

    // Para actualizar:
    // import org.mapstruct.MappingTarget;
    // void updateEntityFromDto(HistorialMedicoCreateDTO dto, @MappingTarget HistorialMedico historialMedico);
}