package com.acured.auditoria.mapper;

import com.acured.auditoria.entity.Auditoria;
import com.acured.common.dto.AuditoriaDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T01:06:33-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class AuditoriaMapperImpl implements AuditoriaMapper {

    @Override
    public AuditoriaDTO toDTO(Auditoria entity) {
        if ( entity == null ) {
            return null;
        }

        AuditoriaDTO auditoriaDTO = new AuditoriaDTO();

        return auditoriaDTO;
    }

    @Override
    public Auditoria toEntity(AuditoriaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Auditoria auditoria = new Auditoria();

        return auditoria;
    }

    @Override
    public void updateEntityFromDto(AuditoriaDTO dto, Auditoria entity) {
        if ( dto == null ) {
            return;
        }
    }
}
