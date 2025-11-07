package com.acured.auditoria.mapper;

import com.acured.auditoria.entity.Auditoria;
import com.acured.common.dto.AuditoriaDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-07T15:32:05-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class AuditoriaMapperImpl implements AuditoriaMapper {

    @Override
    public AuditoriaDTO toDTO(Auditoria entity) {
        if ( entity == null ) {
            return null;
        }

        AuditoriaDTO auditoriaDTO = new AuditoriaDTO();

        auditoriaDTO.setId( entity.getId() );
        auditoriaDTO.setTabla( entity.getTabla() );
        auditoriaDTO.setOperacion( entity.getOperacion() );
        auditoriaDTO.setUsuarioId( entity.getUsuarioId() );
        auditoriaDTO.setFecha( entity.getFecha() );

        return auditoriaDTO;
    }

    @Override
    public Auditoria toEntity(AuditoriaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Auditoria auditoria = new Auditoria();

        auditoria.setId( dto.getId() );
        auditoria.setTabla( dto.getTabla() );
        auditoria.setOperacion( dto.getOperacion() );
        auditoria.setUsuarioId( dto.getUsuarioId() );
        auditoria.setFecha( dto.getFecha() );

        return auditoria;
    }

    @Override
    public void updateEntityFromDto(AuditoriaDTO dto, Auditoria entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getTabla() != null ) {
            entity.setTabla( dto.getTabla() );
        }
        if ( dto.getOperacion() != null ) {
            entity.setOperacion( dto.getOperacion() );
        }
        if ( dto.getUsuarioId() != null ) {
            entity.setUsuarioId( dto.getUsuarioId() );
        }
        if ( dto.getFecha() != null ) {
            entity.setFecha( dto.getFecha() );
        }
    }
}
