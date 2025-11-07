package com.acured.profesionales.mapper;

import com.acured.common.dto.CentroMedicoCreateDTO;
import com.acured.common.dto.CentroMedicoDTO;
import com.acured.profesionales.entity.CentroMedico;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget; // <-- Importar
import org.mapstruct.BeanMapping; // <-- Importar
import org.mapstruct.NullValuePropertyMappingStrategy; // <-- Importar

@Mapper(componentModel = "spring")
public interface CentroMedicoMapper {

    CentroMedicoDTO toDTO(CentroMedico centro);

    CentroMedico toEntity(CentroMedicoCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CentroMedicoCreateDTO dto, @MappingTarget CentroMedico entity);
}