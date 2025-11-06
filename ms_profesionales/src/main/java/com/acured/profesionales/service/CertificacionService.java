package com.acured.profesionales.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acured.common.dto.CertificacionDTO;
import com.acured.profesionales.entity.Certificacion;
import com.acured.profesionales.mapper.CertificacionMapper;
import com.acured.profesionales.repository.CertificacionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificacionService {

    private final CertificacionRepository certificacionRepository;
    private final CertificacionMapper certificacionMapper;

    @Transactional(readOnly = true)
    public List<CertificacionDTO> obtenerTodas() {
        return certificacionRepository.findAll().stream()
                .map(certificacionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CertificacionDTO obtenerPorId(Integer id) {
        Certificacion cert = certificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificación no encontrada con ID: " + id));
        return certificacionMapper.toDTO(cert);
    }

    @Transactional
    public CertificacionDTO guardar(CertificacionDTO dto) {
        Certificacion cert = certificacionMapper.toEntity(dto);
        Certificacion guardada = certificacionRepository.save(cert);
        return certificacionMapper.toDTO(guardada);
    }

    @Transactional
    public CertificacionDTO actualizar(Integer id, CertificacionDTO dto) {
        Certificacion existente = certificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificación no encontrada con ID: " + id));

        certificacionMapper.updateEntityFromDto(dto, existente);
        Certificacion actualizada = certificacionRepository.save(existente);
        return certificacionMapper.toDTO(actualizada);
    }

    @Transactional
    public void eliminar(Integer id) {
        Certificacion cert = certificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificación no encontrada con ID: " + id));
        certificacionRepository.delete(cert);
    }
}