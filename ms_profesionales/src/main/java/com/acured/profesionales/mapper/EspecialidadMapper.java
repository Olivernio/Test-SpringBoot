package com.acured.profesionales.mapper;

import com.acured.common.dto.EspecialidadCreateDTO;
import com.acured.common.dto.EspecialidadDTO;
import com.acured.profesionales.entity.Especialidad;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EspecialidadMapper {

    EspecialidadDTO toDTO(Especialidad especialidad);

    Especialidad toEntity(EspecialidadCreateDTO dto);

    // Para actualizar:
    // import org.mapstruct.MappingTarget;
    // void updateEntityFromDto(EspecialidadCreateDTO dto, @MappingTarget Especialidad especialidad);
}