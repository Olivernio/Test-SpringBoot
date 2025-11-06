package com.acured.profesionales.mapper;

import com.acured.common.dto.EspecialidadCreateDTO;
import com.acured.common.dto.EspecialidadDTO;
import com.acured.profesionales.entity.Especialidad;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T00:23:34-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class EspecialidadMapperImpl implements EspecialidadMapper {

    @Override
    public EspecialidadDTO toDTO(Especialidad especialidad) {
        if ( especialidad == null ) {
            return null;
        }

        EspecialidadDTO especialidadDTO = new EspecialidadDTO();

        return especialidadDTO;
    }

    @Override
    public Especialidad toEntity(EspecialidadCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Especialidad especialidad = new Especialidad();

        return especialidad;
    }
}
