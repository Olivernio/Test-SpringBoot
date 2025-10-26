package com.acured.clinica.mapper;

import com.acured.common.dto.CitaDTO;
import com.acured.clinica.entity.Cita;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.BeanMapping;

@Mapper(componentModel = "spring")
public interface CitaMapper {

    CitaDTO toDTO(Cita entity);

    Cita toEntity(CitaDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CitaDTO dto, @MappingTarget Cita entity);
}