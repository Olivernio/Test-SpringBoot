package com.acured.clinica.mapper;

import com.acured.common.dto.TratamientoCreateDTO;
import com.acured.common.dto.TratamientoDTO;
import com.acured.clinica.entity.Tratamiento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TratamientoMapper {

    // Mapea automáticamente Tratamiento.especialidadId -> TratamientoDTO.especialidadId
    TratamientoDTO toDTO(Tratamiento tratamiento);

    // Mapea automáticamente TratamientoCreateDTO.especialidadId -> Tratamiento.especialidadId
    Tratamiento toEntity(TratamientoCreateDTO dto);

    // Para actualizar:
    // import org.mapstruct.MappingTarget;
    // void updateEntityFromDto(TratamientoCreateDTO dto, @MappingTarget Tratamiento tratamiento);
}