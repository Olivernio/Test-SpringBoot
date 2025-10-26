package com.acured.clinica.mapper;

import com.acured.common.dto.TratamientoDTO;
import com.acured.clinica.entity.Tratamiento;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.BeanMapping;

@Mapper(componentModel = "spring")
public interface TratamientoMapper {

    TratamientoDTO toDTO(Tratamiento entity);

    Tratamiento toEntity(TratamientoDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(TratamientoDTO dto, @MappingTarget Tratamiento entity);
}