package com.acured.clinica.mapper;

import com.acured.common.dto.TratamientoCreateDTO;
import com.acured.common.dto.TratamientoDTO;
import com.acured.clinica.entity.Especialidad;
import com.acured.clinica.entity.Tratamiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TratamientoMapper {

    @Autowired
    private EspecialidadMapper especialidadMapper;

    public TratamientoDTO toDTO(Tratamiento tratamiento) {
        if (tratamiento == null) return null;
        TratamientoDTO dto = new TratamientoDTO();
        dto.setId(tratamiento.getId());
        dto.setNombre(tratamiento.getNombre());
        dto.setDescripcion(tratamiento.getDescripcion());
        dto.setDuracionMin(tratamiento.getDuracionMin());
        dto.setPrecio(tratamiento.getPrecio());
        // Mapea la especialidad anidada si no es nula
        if (tratamiento.getEspecialidad() != null) {
            dto.setEspecialidad(especialidadMapper.toDTO(tratamiento.getEspecialidad()));
        }
        return dto;
    }

    public Tratamiento toEntity(TratamientoCreateDTO dto) {
        if (dto == null) return null;
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setNombre(dto.getNombre());
        tratamiento.setDescripcion(dto.getDescripcion());
        tratamiento.setDuracionMin(dto.getDuracionMin());
        tratamiento.setPrecio(dto.getPrecio());
        // La especialidad se asignará en el Servicio
        return tratamiento;
    }
}