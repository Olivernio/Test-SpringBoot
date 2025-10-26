package com.acured.clinica.service;

import com.acured.clinica.entity.HistorialMedico;
import com.acured.clinica.mapper.HistorialMedicoMapper;
import com.acured.clinica.repository.HistorialMedicoRepository;
import com.acured.clinica.repository.PacienteRepository; // Para validar pacienteId
import com.acured.common.dto.HistorialMedicoCreateDTO;
import com.acured.common.dto.HistorialMedicoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.Getter; // <--- Make sure this is imported
import lombok.Setter; // <--- Make sure this is imported

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistorialMedicoService {

    private final HistorialMedicoRepository historialRepository;
    private final PacienteRepository pacienteRepository; // Para validar FK
    private final HistorialMedicoMapper historialMapper;

    @Transactional(readOnly = true)
    public List<HistorialMedicoDTO> obtenerTodoElHistorial() {
        return historialRepository.findAll().stream()
                .map(historialMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<HistorialMedicoDTO> obtenerHistorialPorId(Integer id) {
        return historialRepository.findById(id)
                .map(historialMapper::toDTO);
    }

    @Transactional
    public HistorialMedicoDTO guardarHistorial(HistorialMedicoCreateDTO dto) {
        // **Validación FK**
        validarPaciente(dto.getPacienteId());

        HistorialMedico historial = historialMapper.toEntity(dto);
        // fecha_registro se maneja con @PrePersist

        HistorialMedico guardado = historialRepository.save(historial);
        return historialMapper.toDTO(guardado);
    }

    @Transactional
    public HistorialMedicoDTO actualizarHistorial(Integer id, HistorialMedicoCreateDTO dto) {
        HistorialMedico existente = historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial médico no encontrado con id: " + id));

        // **Validación FK**
        validarPaciente(dto.getPacienteId());

        // Actualizar campos
        existente.setPacienteId(dto.getPacienteId());
        existente.setDescripcion(dto.getDescripcion());
        existente.setUrlArchivo(dto.getUrlArchivo());
        // fecha_registro normalmente no se actualiza, se mantiene la original

        HistorialMedico actualizado = historialRepository.save(existente);
        return historialMapper.toDTO(actualizado);
    }

    @Transactional
    public void eliminarHistorial(Integer id) {
        if (!historialRepository.existsById(id)) {
            throw new RuntimeException("Historial médico no encontrado con id: " + id);
        }
        // No tiene otras tablas que dependan de él directamente con FK obligatoria
        historialRepository.deleteById(id);
    }

     // --- Método auxiliar de validación ---
    private void validarPaciente(Integer pacienteId) {
        if (pacienteId != null && !pacienteRepository.existsById(pacienteId)) {
            throw new RuntimeException("Paciente no encontrado con ID: " + pacienteId);
        } else if (pacienteId == null) {
             throw new RuntimeException("El ID del paciente no puede ser nulo para un historial.");
        }
    }
}