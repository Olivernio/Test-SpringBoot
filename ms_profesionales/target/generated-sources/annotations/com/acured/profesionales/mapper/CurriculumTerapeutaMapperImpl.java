package com.acured.profesionales.mapper;

import com.acured.common.dto.CurriculumTerapeutaDTO;
import com.acured.profesionales.entity.CurriculumTerapeuta;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T01:06:31-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Eclipse Adoptium)"
)
@Component
public class CurriculumTerapeutaMapperImpl implements CurriculumTerapeutaMapper {

    @Override
    public CurriculumTerapeutaDTO toDTO(CurriculumTerapeuta entity) {
        if ( entity == null ) {
            return null;
        }

        CurriculumTerapeutaDTO curriculumTerapeutaDTO = new CurriculumTerapeutaDTO();

        return curriculumTerapeutaDTO;
    }

    @Override
    public CurriculumTerapeuta toEntity(CurriculumTerapeutaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CurriculumTerapeuta curriculumTerapeuta = new CurriculumTerapeuta();

        return curriculumTerapeuta;
    }

    @Override
    public void updateEntityFromDto(CurriculumTerapeutaDTO dto, CurriculumTerapeuta entity) {
        if ( dto == null ) {
            return;
        }
    }
}
