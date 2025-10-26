package com.acured.clinica.mapper;

import com.acured.common.dto.PacienteDTO;
import com.acured.clinica.entity.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.BeanMapping;

@Mapper(componentModel = "spring")
public interface PacienteMapper {

    PacienteDTO toDTO(Paciente entity);

    Paciente toEntity(PacienteDTO dto);

    // Update existing entity from DTO, ignoring nulls in DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PacienteDTO dto, @MappingTarget Paciente entity);
}