package com.acured.clinica.service;

import com.acured.clinica.entity.Especialidad;
import com.acured.clinica.mapper.EspecialidadMapper;
import com.acured.clinica.repository.EspecialidadRepository;
import com.acured.common.dto.EspecialidadCreateDTO;
import com.acured.common.dto.EspecialidadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private EspecialidadMapper especialidadMapper;

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
        Especialidad guardada = especialidadRepository.save(especialidad);
        return especialidadMapper.toDTO(guardada);
    }

    @Transactional
    public EspecialidadDTO actualizarEspecialidad(Integer id, EspecialidadCreateDTO dto) {
        Optional<Especialidad> existenteOpt = especialidadRepository.findById(id);
        
        if (existenteOpt.isPresent()) {
            Especialidad existente = existenteOpt.get();
            existente.setNombre(dto.getNombre());
            Especialidad actualizada = especialidadRepository.save(existente);
            return especialidadMapper.toDTO(actualizada);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarEspecialidad(Integer id) {
        especialidadRepository.deleteById(id);
    }
}