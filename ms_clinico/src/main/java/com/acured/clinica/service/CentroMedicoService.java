package com.acured.clinica.service;

import com.acured.clinica.entity.CentroMedico;
import com.acured.clinica.mapper.CentroMedicoMapper;
import com.acured.clinica.repository.CentroMedicoRepository;
import com.acured.common.dto.CentroMedicoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CentroMedicoService {

    private final CentroMedicoRepository centroMedicoRepository;
    private final CentroMedicoMapper centroMedicoMapper;

    @Transactional(readOnly = true)
    public List<CentroMedicoDTO> obtenerTodosLosCentros() {
        return centroMedicoRepository.findAll().stream()
                .map(centroMedicoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CentroMedicoDTO obtenerCentroPorId(Integer id) {
        CentroMedico centro = centroMedicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Centro Médico no encontrado con ID: " + id));
        return centroMedicoMapper.toDTO(centro);
    }

    @Transactional
    public CentroMedicoDTO guardarCentro(CentroMedicoDTO dto) {
        // Add validation if needed (e.g., check if paisId exists)
        CentroMedico centro = centroMedicoMapper.toEntity(dto);
        CentroMedico guardado = centroMedicoRepository.save(centro);
        return centroMedicoMapper.toDTO(guardado);
    }

    @Transactional
    public CentroMedicoDTO actualizarCentro(Integer id, CentroMedicoDTO dto) {
        CentroMedico existente = centroMedicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Centro Médico no encontrado con ID: " + id));

        centroMedicoMapper.updateEntityFromDto(dto, existente);

        CentroMedico actualizado = centroMedicoRepository.save(existente);
        return centroMedicoMapper.toDTO(actualizado);
    }

    @Transactional
    public void eliminarCentro(Integer id) {
        CentroMedico centro = centroMedicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Centro Médico no encontrado con ID: " + id));
        // Consider related data (e.g., Citas) before deleting or use cascading deletes carefully
        centroMedicoRepository.delete(centro);
    }
}