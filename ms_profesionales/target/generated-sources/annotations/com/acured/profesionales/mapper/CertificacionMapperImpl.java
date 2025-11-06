package com.acured.profesionales.mapper;

import com.acured.common.dto.CertificacionDTO;
import com.acured.profesionales.entity.Certificacion;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T01:06:31-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class CertificacionMapperImpl implements CertificacionMapper {

    @Override
    public CertificacionDTO toDTO(Certificacion entity) {
        if ( entity == null ) {
            return null;
        }

        CertificacionDTO certificacionDTO = new CertificacionDTO();

        return certificacionDTO;
    }

    @Override
    public Certificacion toEntity(CertificacionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Certificacion certificacion = new Certificacion();

        return certificacion;
    }

    @Override
    public void updateEntityFromDto(CertificacionDTO dto, Certificacion entity) {
        if ( dto == null ) {
            return;
        }
    }
}
