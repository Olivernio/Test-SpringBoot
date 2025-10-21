package com.acured.clinica.service;

import com.acured.clinica.entity.Especialidad;
import com.acured.clinica.entity.Tratamiento;
import com.acured.clinica.mapper.TratamientoMapper;
import com.acured.clinica.repository.EspecialidadRepository;
import com.acured.clinica.repository.TratamientoRepository;
import com.acured.common.dto.TratamientoCreateDTO;
import com.acured.common.dto.TratamientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TratamientoService {

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository; // Necesario para buscar por ID

    @Autowired
    private TratamientoMapper tratamientoMapper;

    @Transactional(readOnly = true)
    public List<TratamientoDTO> obtenerTodosLosTratamientos() {
        return tratamientoRepository.findAll().stream()
                .map(tratamientoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<TratamientoDTO> obtenerTratamientoPorId(Integer id) {
        return tratamientoRepository.findById(id)
                .map(tratamientoMapper::toDTO);
    }

    @Transactional
    public TratamientoDTO guardarTratamiento(TratamientoCreateDTO dto) {
        Tratamiento tratamiento = tratamientoMapper.toEntity(dto);
        
        // Asignamos la especialidad desde el ID
        if (dto.getEspecialidadId() != null) {
            Especialidad especialidad = especialidadRepository.findById(dto.getEspecialidadId())
                    .orElseThrow(() -> new RuntimeException("Especialidad no encontrada")); // O manejo de error
            tratamiento.setEspecialidad(especialidad);
        }
        
        Tratamiento guardado = tratamientoRepository.save(tratamiento);
        return tratamientoMapper.toDTO(guardado);
    }

    @Transactional
    public TratamientoDTO actualizarTratamiento(Integer id, TratamientoCreateDTO dto) {
        Optional<Tratamiento> existenteOpt = tratamientoRepository.findById(id);
        
        if (existenteOpt.isPresent()) {
            Tratamiento existente = existenteOpt.get();
            existente.setNombre(dto.getNombre());
            existente.setDescripcion(dto.getDescripcion());
            existente.setDuracionMin(dto.getDuracionMin());
            existente.setPrecio(dto.getPrecio());
            
            // Asignamos la especialidad desde el ID
            if (dto.getEspecialidadId() != null) {
                Especialidad especialidad = especialidadRepository.findById(dto.getEspecialidadId())
                        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
                existente.setEspecialidad(especialidad);
            } else {
                existente.setEspecialidad(null);
            }
            
            Tratamiento actualizado = tratamientoRepository.save(existente);
            return tratamientoMapper.toDTO(actualizado);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarTratamiento(Integer id) {
        tratamientoRepository.deleteById(id);
    }
}