package com.acured.clinica.mapper;

import com.acured.common.dto.HistorialMedicoDTO;
import com.acured.clinica.entity.HistorialMedico;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.BeanMapping;

@Mapper(componentModel = "spring")
public interface HistorialMedicoMapper {

    HistorialMedicoDTO toDTO(HistorialMedico entity);

    HistorialMedico toEntity(HistorialMedicoDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(HistorialMedicoDTO dto, @MappingTarget HistorialMedico entity);
}