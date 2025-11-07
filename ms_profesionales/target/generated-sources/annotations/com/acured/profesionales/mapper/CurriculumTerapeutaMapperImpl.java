package com.acured.profesionales.mapper;

import com.acured.common.dto.CurriculumTerapeutaDTO;
import com.acured.profesionales.entity.CurriculumTerapeuta;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-07T15:31:58-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class CurriculumTerapeutaMapperImpl implements CurriculumTerapeutaMapper {

    @Override
    public CurriculumTerapeutaDTO toDTO(CurriculumTerapeuta entity) {
        if ( entity == null ) {
            return null;
        }

        CurriculumTerapeutaDTO curriculumTerapeutaDTO = new CurriculumTerapeutaDTO();

        curriculumTerapeutaDTO.setId( entity.getId() );
        curriculumTerapeutaDTO.setTerapeutaId( entity.getTerapeutaId() );
        curriculumTerapeutaDTO.setInstitucion( entity.getInstitucion() );
        curriculumTerapeutaDTO.setCargo( entity.getCargo() );
        curriculumTerapeutaDTO.setDescripcion( entity.getDescripcion() );
        curriculumTerapeutaDTO.setFechaInicio( entity.getFechaInicio() );
        curriculumTerapeutaDTO.setFechaFin( entity.getFechaFin() );

        return curriculumTerapeutaDTO;
    }

    @Override
    public CurriculumTerapeuta toEntity(CurriculumTerapeutaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CurriculumTerapeuta curriculumTerapeuta = new CurriculumTerapeuta();

        curriculumTerapeuta.setId( dto.getId() );
        curriculumTerapeuta.setTerapeutaId( dto.getTerapeutaId() );
        curriculumTerapeuta.setInstitucion( dto.getInstitucion() );
        curriculumTerapeuta.setCargo( dto.getCargo() );
        curriculumTerapeuta.setDescripcion( dto.getDescripcion() );
        curriculumTerapeuta.setFechaInicio( dto.getFechaInicio() );
        curriculumTerapeuta.setFechaFin( dto.getFechaFin() );

        return curriculumTerapeuta;
    }

    @Override
    public void updateEntityFromDto(CurriculumTerapeutaDTO dto, CurriculumTerapeuta entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getTerapeutaId() != null ) {
            entity.setTerapeutaId( dto.getTerapeutaId() );
        }
        if ( dto.getInstitucion() != null ) {
            entity.setInstitucion( dto.getInstitucion() );
        }
        if ( dto.getCargo() != null ) {
            entity.setCargo( dto.getCargo() );
        }
        if ( dto.getDescripcion() != null ) {
            entity.setDescripcion( dto.getDescripcion() );
        }
        if ( dto.getFechaInicio() != null ) {
            entity.setFechaInicio( dto.getFechaInicio() );
        }
        if ( dto.getFechaFin() != null ) {
            entity.setFechaFin( dto.getFechaFin() );
        }
    }
}
