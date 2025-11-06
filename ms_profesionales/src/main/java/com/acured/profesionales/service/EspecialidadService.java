package com.acured.profesionales.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acured.common.dto.EspecialidadCreateDTO;
import com.acured.common.dto.EspecialidadDTO;
import com.acured.profesionales.entity.Especialidad;
import com.acured.profesionales.mapper.EspecialidadMapper; // <--- Make sure this is imported
import com.acured.profesionales.repository.EspecialidadRepository; // <--- Make sure this is imported

import lombok.RequiredArgsConstructor;

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
    public Optional<EspecialidadDTO> obtenerEspecialidadPorId(Integer id) {
        return especialidadRepository.findById(id)
                .map(especialidadMapper::toDTO);
    }

    @Transactional
    public EspecialidadDTO guardarEspecialidad(EspecialidadCreateDTO dto) {
        Especialidad especialidad = especialidadMapper.toEntity(dto);
        try {
            Especialidad guardada = especialidadRepository.save(especialidad);
            return especialidadMapper.toDTO(guardada);
        } catch (DataIntegrityViolationException e) {
            // Captura error si el nombre ya existe (unique constraint del SQL)
            throw new RuntimeException("La especialidad con nombre '" + dto.getNombre() + "' ya existe.", e);
        }
    }

    @Transactional
    public EspecialidadDTO actualizarEspecialidad(Integer id, EspecialidadCreateDTO dto) {
        Especialidad existente = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con id: " + id));

        existente.setNombre(dto.getNombre());

        try {
            Especialidad actualizada = especialidadRepository.save(existente);
            return especialidadMapper.toDTO(actualizada);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("El nombre de especialidad '" + dto.getNombre() + "' ya está en uso.", e);
        }
    }

    @Transactional
    public void eliminarEspecialidad(Integer id) {
        if (!especialidadRepository.existsById(id)) {
            throw new RuntimeException("Especialidad no encontrada con id: " + id);
        }
        // El SQL tiene ON DELETE SET NULL para tratamiento.especialidad_id
        // Debería funcionar, pero capturamos por si acaso.
        try {
            especialidadRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // Esto no debería pasar con SET NULL
             throw new RuntimeException("No se puede eliminar la especialidad, aún tiene referencias.", e);
        }
    }
}