package com.acured.clinica.mapper;

import com.acured.common.dto.CitaCreateDTO;
import com.acured.common.dto.CitaDTO;
import com.acured.clinica.entity.Cita;
import org.mapstruct.Mapper;
// import org.mapstruct.Mapping; // Ya no es necesario si los nombres coinciden

@Mapper(componentModel = "spring")
public interface CitaMapper {

    // MapStruct mapea automáticamente campos con nombres iguales:
    // Cita.pacienteId -> CitaDTO.pacienteId
    // Cita.centroId -> CitaDTO.centroId
    // Cita.terapeutaId -> CitaDTO.terapeutaId
    // etc.
    CitaDTO toDTO(Cita cita);

    // MapStruct mapea automáticamente:
    // CitaCreateDTO.pacienteId -> Cita.pacienteId
    // CitaCreateDTO.centroId -> Cita.centroId
    // CitaCreateDTO.terapeutaId -> Cita.terapeutaId
    // etc.
    Cita toEntity(CitaCreateDTO dto);

    // Para actualizar:
    // import org.mapstruct.MappingTarget;
    // void updateEntityFromDto(CitaCreateDTO dto, @MappingTarget Cita cita);
}