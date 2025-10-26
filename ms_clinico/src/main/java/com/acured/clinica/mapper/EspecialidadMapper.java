package com.acured.clinica.mapper;

import com.acured.common.dto.EspecialidadDTO;
import com.acured.clinica.entity.Especialidad;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.BeanMapping;

@Mapper(componentModel = "spring")
public interface EspecialidadMapper {

    EspecialidadDTO toDTO(Especialidad entity);

    Especialidad toEntity(EspecialidadDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EspecialidadDTO dto, @MappingTarget Especialidad entity);
}