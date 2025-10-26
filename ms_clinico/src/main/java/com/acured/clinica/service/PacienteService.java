package com.acured.clinica.service;

import com.acured.clinica.entity.Paciente;
import com.acured.clinica.mapper.PacienteMapper;
import com.acured.clinica.repository.PacienteRepository;
import com.acured.common.dto.PacienteDTO;
// Consider creating a custom exception in common module
// import com.acured.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;

    @Transactional(readOnly = true)
    public List<PacienteDTO> obtenerTodosLosPacientes() {
        return pacienteRepository.findAll()
                .stream()
                .map(pacienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PacienteDTO obtenerPacientePorId(Integer id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + id)); // Replace with ResourceNotFoundException
        return pacienteMapper.toDTO(paciente);
    }

    @Transactional
    public PacienteDTO guardarPaciente(PacienteDTO pacienteDTO) {
        // Add business logic validation if needed (e.g., check if usuarioId is valid via Feign Client)
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        return pacienteMapper.toDTO(pacienteGuardado);
    }

    @Transactional
    public PacienteDTO actualizarPaciente(Integer id, PacienteDTO pacienteDTO) {
        Paciente pacienteExistente = pacienteRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + id)); // Replace with ResourceNotFoundException

        pacienteMapper.updateEntityFromDto(pacienteDTO, pacienteExistente);

        Paciente pacienteActualizado = pacienteRepository.save(pacienteExistente);
        return pacienteMapper.toDTO(pacienteActualizado);
    }

    @Transactional
    public void eliminarPaciente(Integer id) {
         Paciente paciente = pacienteRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + id)); // Check existence before delete
        pacienteRepository.delete(paciente);
    }
}