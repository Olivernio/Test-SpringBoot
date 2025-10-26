package com.acured.clinica.mapper;

import com.acured.common.dto.CentroMedicoDTO;
import com.acured.clinica.entity.CentroMedico;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.BeanMapping;

@Mapper(componentModel = "spring")
public interface CentroMedicoMapper {

    CentroMedicoDTO toDTO(CentroMedico entity);

    CentroMedico toEntity(CentroMedicoDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CentroMedicoDTO dto, @MappingTarget CentroMedico entity);
}