package com.acured.clinica.service;

import com.acured.clinica.entity.HistorialMedico;
import com.acured.clinica.mapper.HistorialMedicoMapper;
import com.acured.clinica.repository.HistorialMedicoRepository;
import com.acured.clinica.repository.PacienteRepository; // Needed for check
import com.acured.common.dto.HistorialMedicoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime; // For setting date
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistorialMedicoService {

    private final HistorialMedicoRepository historialRepository;
    private final PacienteRepository pacienteRepository; // To validate pacienteId
    private final HistorialMedicoMapper historialMapper;

    @Transactional(readOnly = true)
    public List<HistorialMedicoDTO> obtenerTodoElHistorial() {
        return historialRepository.findAll().stream()
                .map(historialMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public HistorialMedicoDTO obtenerHistorialPorId(Integer id) {
        HistorialMedico historial = historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial Médico no encontrado con ID: " + id));
        return historialMapper.toDTO(historial);
    }

    @Transactional
    public HistorialMedicoDTO guardarHistorial(HistorialMedicoDTO dto) {
        // Validate pacienteId exists
        pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + dto.getPacienteId()));

        HistorialMedico historial = historialMapper.toEntity(dto);
        // Set registration date if not handled by DB default or if needed before save
        if (historial.getFechaRegistro() == null) {
            historial.setFechaRegistro(LocalDateTime.now());
        }
        HistorialMedico guardado = historialRepository.save(historial);
        return historialMapper.toDTO(guardado);
    }

    @Transactional
    public HistorialMedicoDTO actualizarHistorial(Integer id, HistorialMedicoDTO dto) {
        HistorialMedico existente = historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial Médico no encontrado con ID: " + id));

        // Validate pacienteId if changed
        if (dto.getPacienteId() != null && !dto.getPacienteId().equals(existente.getPacienteId())) {
             pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + dto.getPacienteId()));
        }

        // Don't update fechaRegistro typically
        dto.setFechaRegistro(null); // Ensure MapStruct doesn't overwrite it if null handling is set
        historialMapper.updateEntityFromDto(dto, existente); // MapStruct handles update

        HistorialMedico actualizado = historialRepository.save(existente);
        return historialMapper.toDTO(actualizado);
    }

    @Transactional
    public void eliminarHistorial(Integer id) {
        HistorialMedico historial = historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial Médico no encontrado con ID: " + id));
        historialRepository.delete(historial);
    }
}