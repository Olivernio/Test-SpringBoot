package com.acured.auditoria.mapper;

import com.acured.common.dto.AuditoriaDTO;
import com.acured.auditoria.entity.Auditoria;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring") // Le dice a MapStruct que es un componente de Spring
public interface AuditoriaMapper {

    // Convierte Entidad -> DTO
    AuditoriaDTO toDTO(Auditoria entity);

    // Convierte DTO -> Entidad
    Auditoria toEntity(AuditoriaDTO dto);

    // Actualiza una entidad existente con los datos de un DTO
    // (Aunque no usaremos 'actualizar' en auditoría, es bueno tenerlo
    // por consistencia si MapStruct se queja en el Service)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AuditoriaDTO dto, @MappingTarget Auditoria entity);
}