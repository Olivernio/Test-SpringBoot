package com.acured.profesionales.mapper;

import com.acured.common.dto.CertificacionDTO;
import com.acured.profesionales.entity.Certificacion;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-07T15:31:58-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class CertificacionMapperImpl implements CertificacionMapper {

    @Override
    public CertificacionDTO toDTO(Certificacion entity) {
        if ( entity == null ) {
            return null;
        }

        CertificacionDTO certificacionDTO = new CertificacionDTO();

        certificacionDTO.setId( entity.getId() );
        certificacionDTO.setNombre( entity.getNombre() );
        certificacionDTO.setInstitucion( entity.getInstitucion() );
        certificacionDTO.setDescripcion( entity.getDescripcion() );

        return certificacionDTO;
    }

    @Override
    public Certificacion toEntity(CertificacionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Certificacion certificacion = new Certificacion();

        certificacion.setId( dto.getId() );
        certificacion.setNombre( dto.getNombre() );
        certificacion.setInstitucion( dto.getInstitucion() );
        certificacion.setDescripcion( dto.getDescripcion() );

        return certificacion;
    }

    @Override
    public void updateEntityFromDto(CertificacionDTO dto, Certificacion entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getNombre() != null ) {
            entity.setNombre( dto.getNombre() );
        }
        if ( dto.getInstitucion() != null ) {
            entity.setInstitucion( dto.getInstitucion() );
        }
        if ( dto.getDescripcion() != null ) {
            entity.setDescripcion( dto.getDescripcion() );
        }
    }
}
