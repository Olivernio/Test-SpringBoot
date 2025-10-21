package com.acured.clinica.mapper;

import com.acured.common.dto.EspecialidadCreateDTO;
import com.acured.common.dto.EspecialidadDTO;
import com.acured.clinica.entity.Especialidad;
import org.springframework.stereotype.Component;

@Component
public class EspecialidadMapper {

    public EspecialidadDTO toDTO(Especialidad especialidad) {
        if (especialidad == null) return null;
        EspecialidadDTO dto = new EspecialidadDTO();
        dto.setId(especialidad.getId());
        dto.setNombre(especialidad.getNombre());
        return dto;
    }

    public Especialidad toEntity(EspecialidadCreateDTO dto) {
        if (dto == null) return null;
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(dto.getNombre());
        return especialidad;
    }
}