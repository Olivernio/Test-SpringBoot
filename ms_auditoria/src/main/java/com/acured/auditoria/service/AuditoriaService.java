package com.acured.auditoria.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acured.auditoria.entity.Auditoria;
import com.acured.auditoria.mapper.AuditoriaMapper;
import com.acured.auditoria.repository.AuditoriaRepository;
import com.acured.common.dto.AuditoriaDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Inyecta las dependencias finales
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;
    private final AuditoriaMapper auditoriaMapper;

    /**
     * Obtiene todos los registros de auditoría.
     * (En una app real, esto debería estar paginado).
     */
    @Transactional(readOnly = true)
    public List<AuditoriaDTO> obtenerTodo() {
        return auditoriaRepository.findAll().stream()
                .map(auditoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un registro de auditoría por su ID.
     */
    @Transactional(readOnly = true)
    public AuditoriaDTO obtenerPorId(Integer id) {
        Auditoria auditoria = auditoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de auditoría no encontrado con ID: " + id));
        return auditoriaMapper.toDTO(auditoria);
    }

    /**
     * Guarda un nuevo registro de auditoría.
     * Este será el método más usado.
     */
    @Transactional
    public AuditoriaDTO guardarRegistro(AuditoriaDTO dto) {
        Auditoria auditoria = auditoriaMapper.toEntity(dto);

        if (auditoria.getFecha() == null) {
            auditoria.setFecha(LocalDateTime.now());
        }


        Auditoria guardada = auditoriaRepository.save(auditoria);
        return auditoriaMapper.toDTO(guardada);
    }

}