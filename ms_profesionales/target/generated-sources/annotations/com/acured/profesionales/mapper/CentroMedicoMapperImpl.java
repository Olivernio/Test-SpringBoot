package com.acured.profesionales.mapper;

import com.acured.common.dto.CentroMedicoCreateDTO;
import com.acured.common.dto.CentroMedicoDTO;
import com.acured.profesionales.entity.CentroMedico;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T01:06:31-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class CentroMedicoMapperImpl implements CentroMedicoMapper {

    @Override
    public CentroMedicoDTO toDTO(CentroMedico centro) {
        if ( centro == null ) {
            return null;
        }

        CentroMedicoDTO centroMedicoDTO = new CentroMedicoDTO();

        return centroMedicoDTO;
    }

    @Override
    public CentroMedico toEntity(CentroMedicoCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CentroMedico centroMedico = new CentroMedico();

        return centroMedico;
    }
}
