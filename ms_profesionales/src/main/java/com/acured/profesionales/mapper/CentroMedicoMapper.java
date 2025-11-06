package com.acured.profesionales.mapper;

import com.acured.common.dto.CentroMedicoCreateDTO;
import com.acured.common.dto.CentroMedicoDTO;
import com.acured.profesionales.entity.CentroMedico;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CentroMedicoMapper {

    CentroMedicoDTO toDTO(CentroMedico centro);

    CentroMedico toEntity(CentroMedicoCreateDTO dto);

}