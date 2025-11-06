package com.acured.profesionales.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acured.common.dto.CurriculumTerapeutaDTO;
import com.acured.profesionales.entity.CurriculumTerapeuta;
import com.acured.profesionales.mapper.CurriculumTerapeutaMapper;
import com.acured.profesionales.repository.CurriculumTerapeutaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurriculumTerapeutaService {

    private final CurriculumTerapeutaRepository curriculumRepository;
    private final CurriculumTerapeutaMapper curriculumMapper;

    @Transactional(readOnly = true)
    public List<CurriculumTerapeutaDTO> obtenerTodos() {
        return curriculumRepository.findAll().stream()
                .map(curriculumMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CurriculumTerapeutaDTO obtenerPorId(Integer id) {
        CurriculumTerapeuta curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de currículum no encontrado con ID: " + id));
        return curriculumMapper.toDTO(curriculum);
    }
    
    @Transactional(readOnly = true)
    public List<CurriculumTerapeutaDTO> obtenerPorTerapeutaId(Integer terapeutaId) {
        return curriculumRepository.findByTerapeutaId(terapeutaId).stream()
                .map(curriculumMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CurriculumTerapeutaDTO guardar(CurriculumTerapeutaDTO dto) {

        CurriculumTerapeuta curriculum = curriculumMapper.toEntity(dto);
        CurriculumTerapeuta guardado = curriculumRepository.save(curriculum);
        return curriculumMapper.toDTO(guardado);
    }

    @Transactional
    public CurriculumTerapeutaDTO actualizar(Integer id, CurriculumTerapeutaDTO dto) {
        CurriculumTerapeuta existente = curriculumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de currículum no encontrado con ID: " + id));

        if (dto.getTerapeutaId() != null && !dto.getTerapeutaId().equals(existente.getTerapeutaId())) {
        }

        curriculumMapper.updateEntityFromDto(dto, existente);
        CurriculumTerapeuta actualizado = curriculumRepository.save(existente);
        return curriculumMapper.toDTO(actualizado);
    }

    @Transactional
    public void eliminar(Integer id) {
        CurriculumTerapeuta curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de currículum no encontrado con ID: " + id));
        curriculumRepository.delete(curriculum);
    }

}