package com.acured.profesionales.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.acured.common.dto.CurriculumTerapeutaDTO;
import com.acured.profesionales.entity.CurriculumTerapeuta;

@Mapper(componentModel = "spring")
public interface CurriculumTerapeutaMapper {

    // Convierte Entidad -> DTO
    CurriculumTerapeutaDTO toDTO(CurriculumTerapeuta entity);

    // Convierte DTO -> Entidad
    CurriculumTerapeuta toEntity(CurriculumTerapeutaDTO dto);

    // Actualiza una entidad existente con los datos de un DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CurriculumTerapeutaDTO dto, @MappingTarget CurriculumTerapeuta entity);
}