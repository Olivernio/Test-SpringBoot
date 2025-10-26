package com.acured.clinica.service;

import com.acured.clinica.entity.Especialidad; // Needed for check
import com.acured.clinica.entity.Tratamiento;
import com.acured.clinica.mapper.TratamientoMapper;
import com.acured.clinica.repository.EspecialidadRepository; // Needed for check
import com.acured.clinica.repository.TratamientoRepository;
import com.acured.common.dto.TratamientoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TratamientoService {

    private final TratamientoRepository tratamientoRepository;
    private final EspecialidadRepository especialidadRepository; // To validate especialidadId
    private final TratamientoMapper tratamientoMapper;

    @Transactional(readOnly = true)
    public List<TratamientoDTO> obtenerTodosLosTratamientos() {
        return tratamientoRepository.findAll().stream()
                .map(tratamientoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TratamientoDTO obtenerTratamientoPorId(Integer id) {
        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con ID: " + id));
        return tratamientoMapper.toDTO(tratamiento);
    }

    @Transactional
    public TratamientoDTO guardarTratamiento(TratamientoDTO dto) {
        // Validate especialidadId exists
        especialidadRepository.findById(dto.getEspecialidadId())
            .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + dto.getEspecialidadId()));

        Tratamiento tratamiento = tratamientoMapper.toEntity(dto);
        Tratamiento guardado = tratamientoRepository.save(tratamiento);
        return tratamientoMapper.toDTO(guardado);
    }

    @Transactional
    public TratamientoDTO actualizarTratamiento(Integer id, TratamientoDTO dto) {
        Tratamiento existente = tratamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con ID: " + id));

        // Validate new especialidadId if provided and changed
        if (dto.getEspecialidadId() != null && !dto.getEspecialidadId().equals(existente.getEspecialidadId())) {
             especialidadRepository.findById(dto.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + dto.getEspecialidadId()));
        }

        tratamientoMapper.updateEntityFromDto(dto, existente); // MapStruct handles update

        Tratamiento actualizado = tratamientoRepository.save(existente);
        return tratamientoMapper.toDTO(actualizado);
    }

    @Transactional
    public void eliminarTratamiento(Integer id) {
        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con ID: " + id));
        // Consider related DetalleCitaTratamiento or ServicioCentro before deleting
        tratamientoRepository.delete(tratamiento);
    }
}