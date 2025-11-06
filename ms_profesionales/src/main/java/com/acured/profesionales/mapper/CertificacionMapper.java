package com.acured.profesionales.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.acured.common.dto.CertificacionDTO;
import com.acured.profesionales.entity.Certificacion;

@Mapper(componentModel = "spring") // Le dice a MapStruct que es un componente de Spring
public interface CertificacionMapper {

    // Convierte Entidad -> DTO
    CertificacionDTO toDTO(Certificacion entity);

    // Convierte DTO -> Entidad
    Certificacion toEntity(CertificacionDTO dto);

    // Actualiza una entidad existente con los datos de un DTO
    // Ignora los campos que vengan nulos en el DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CertificacionDTO dto, @MappingTarget Certificacion entity);
}