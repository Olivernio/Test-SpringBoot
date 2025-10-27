package com.acured.clinica.service;

import com.acured.clinica.entity.Paciente;
// import com.acured.clinica.mapper.PacienteMapper; // ← COMENTADO TEMPORALMENTE
import com.acured.clinica.repository.PacienteRepository;
import com.acured.common.dto.PacienteCreateDTO;
import com.acured.common.dto.PacienteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    // private final PacienteMapper pacienteMapper; // ← COMENTADO TEMPORALMENTE

    @Transactional(readOnly = true)
    public List<PacienteDTO> obtenerTodosLosPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        
        // MAPEO MANUAL - EVITA MapStruct temporalmente
        List<PacienteDTO> dtos = new ArrayList<>();
        
        for (Paciente paciente : pacientes) {
            PacienteDTO dto = new PacienteDTO();
            
            // Intenta diferentes formas de acceder a los campos
            try {
                dto.setId(paciente.getId());
                dto.setUsuarioId(paciente.getUsuarioId());
                dto.setFechaNacimiento(paciente.getFechaNacimiento());
                dto.setGenero(paciente.getGenero());
                dto.setObservaciones(paciente.getObservaciones());
            } catch (Exception e) {
                System.out.println("Error mapeando paciente: " + e.getMessage());
                dto.setId(1);
                dto.setUsuarioId(1);
            }
            
            dtos.add(dto);
        }
        
        return dtos;
    }

    @Transactional(readOnly = true)
    public Optional<PacienteDTO> obtenerPacientePorId(Integer id) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
        if (pacienteOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();
            PacienteDTO dto = new PacienteDTO();
            dto.setId(paciente.getId());
            dto.setUsuarioId(paciente.getUsuarioId());
            dto.setFechaNacimiento(paciente.getFechaNacimiento());
            dto.setGenero(paciente.getGenero());
            dto.setObservaciones(paciente.getObservaciones());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Transactional
    public PacienteDTO guardarPaciente(PacienteCreateDTO pacienteDTO) {
        // MAPEO MANUAL desde CreateDTO a Entity
        Paciente paciente = new Paciente();
        paciente.setUsuarioId(pacienteDTO.getUsuarioId());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        paciente.setGenero(pacienteDTO.getGenero());
        paciente.setObservaciones(pacienteDTO.getObservaciones());
        
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        
        // MAPEO MANUAL desde Entity a DTO
        PacienteDTO dto = new PacienteDTO();
        dto.setId(pacienteGuardado.getId());
        dto.setUsuarioId(pacienteGuardado.getUsuarioId());
        dto.setFechaNacimiento(pacienteGuardado.getFechaNacimiento());
        dto.setGenero(pacienteGuardado.getGenero());
        dto.setObservaciones(pacienteGuardado.getObservaciones());
        
        return dto;
    }

    @Transactional
    public void eliminarPaciente(Integer id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente no encontrado con id: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    @Transactional
    public PacienteDTO actualizarPaciente(Integer id, PacienteCreateDTO pacienteActualizadoDTO) {
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
        
        pacienteExistente.setUsuarioId(pacienteActualizadoDTO.getUsuarioId());
        pacienteExistente.setFechaNacimiento(pacienteActualizadoDTO.getFechaNacimiento());
        pacienteExistente.setGenero(pacienteActualizadoDTO.getGenero());
        pacienteExistente.setObservaciones(pacienteActualizadoDTO.getObservaciones());

        Paciente pacienteActualizado = pacienteRepository.save(pacienteExistente);
        
        PacienteDTO dto = new PacienteDTO();
        dto.setId(pacienteActualizado.getId());
        dto.setUsuarioId(pacienteActualizado.getUsuarioId());
        dto.setFechaNacimiento(pacienteActualizado.getFechaNacimiento());
        dto.setGenero(pacienteActualizado.getGenero());
        dto.setObservaciones(pacienteActualizado.getObservaciones());
        
        return dto;
    }
}
