package com.acured.clinica.mapper;

import com.acured.common.dto.CentroMedicoCreateDTO;
import com.acured.common.dto.CentroMedicoDTO;
import com.acured.clinica.entity.CentroMedico;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CentroMedicoMapper {

    CentroMedicoDTO toDTO(CentroMedico centro);

    CentroMedico toEntity(CentroMedicoCreateDTO dto);

    // Para actualizar:
    // import org.mapstruct.MappingTarget;
    // void updateEntityFromDto(CentroMedicoCreateDTO dto, @MappingTarget CentroMedico centroMedico);
}