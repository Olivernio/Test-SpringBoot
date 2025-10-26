package com.acured.clinica.mapper;

import com.acured.common.dto.PacienteCreateDTO;
import com.acured.common.dto.PacienteDTO;
import com.acured.clinica.entity.Paciente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // Indica a MapStruct que genere una implementación Spring Bean
public interface PacienteMapper {

    // MapStruct mapeará automáticamente los campos con el mismo nombre
    PacienteDTO toDTO(Paciente paciente);

    // MapStruct mapeará automáticamente los campos con el mismo nombre
    Paciente toEntity(PacienteCreateDTO dto);

    // Si necesitaras actualizar una entidad existente desde un DTO, podrías añadir:
    // import org.mapstruct.MappingTarget;
    // void updateEntityFromDto(PacienteCreateDTO dto, @MappingTarget Paciente paciente);
}