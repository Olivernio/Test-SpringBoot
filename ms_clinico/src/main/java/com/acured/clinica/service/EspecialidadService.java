package com.acured.clinica.service;

import com.acured.clinica.entity.Especialidad;
import com.acured.clinica.mapper.EspecialidadMapper;
import com.acured.clinica.repository.EspecialidadRepository;
import com.acured.common.dto.EspecialidadDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;
    private final EspecialidadMapper especialidadMapper;

    @Transactional(readOnly = true)
    public List<EspecialidadDTO> obtenerTodasLasEspecialidades() {
        return especialidadRepository.findAll().stream()
                .map(especialidadMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EspecialidadDTO obtenerEspecialidadPorId(Integer id) {
        Especialidad especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));
        return especialidadMapper.toDTO(especialidad);
    }

    @Transactional
    public EspecialidadDTO guardarEspecialidad(EspecialidadDTO dto) {
        Especialidad especialidad = especialidadMapper.toEntity(dto);
        Especialidad guardada = especialidadRepository.save(especialidad);
        return especialidadMapper.toDTO(guardada);
    }

    @Transactional
    public EspecialidadDTO actualizarEspecialidad(Integer id, EspecialidadDTO dto) {
        Especialidad existente = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));

        especialidadMapper.updateEntityFromDto(dto, existente); // MapStruct handles update

        Especialidad actualizada = especialidadRepository.save(existente);
        return especialidadMapper.toDTO(actualizada);
    }

    @Transactional
    public void eliminarEspecialidad(Integer id) {
        Especialidad especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));
        // Consider related Tratamientos before deleting
        especialidadRepository.delete(especialidad);
    }
}