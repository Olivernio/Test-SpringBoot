package com.acured.clinica.service;

import com.acured.clinica.entity.Cita;
import com.acured.clinica.mapper.CitaMapper;
import com.acured.clinica.repository.CitaRepository;
import com.acured.clinica.repository.CentroMedicoRepository; // Needed for check
import com.acured.clinica.repository.PacienteRepository;   // Needed for check
// Potentially import UserClient if validating terapeutaId
import com.acured.common.dto.CitaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository; // To validate pacienteId
    private final CentroMedicoRepository centroMedicoRepository; // To validate centroId
    private final CitaMapper citaMapper;
    // private final UserClient userClient; // Inject if validating terapeutaId

    @Transactional(readOnly = true)
    public List<CitaDTO> obtenerTodasLasCitas() {
        return citaRepository.findAll().stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CitaDTO obtenerCitaPorId(Integer id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        return citaMapper.toDTO(cita);
    }

    @Transactional
    public CitaDTO guardarCita(CitaDTO dto) {
        // Validate foreign keys exist
        pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + dto.getPacienteId()));
        centroMedicoRepository.findById(dto.getCentroId())
                .orElseThrow(() -> new RuntimeException("Centro Médico no encontrado con ID: " + dto.getCentroId()));
        // Optional: Validate terapeutaId using UserClient if implemented
        // userClient.findUsuarioById(dto.getTerapeutaId()); // Feign throws exception if 404

        Cita cita = citaMapper.toEntity(dto);
        // Set default status if needed and not handled by DB default
        if (cita.getEstado() == null) {
             cita.setEstado("pendiente");
        }
        Cita guardada = citaRepository.save(cita);
        return citaMapper.toDTO(guardada);
    }

    @Transactional
    public CitaDTO actualizarCita(Integer id, CitaDTO dto) {
        Cita citaExistente = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));

        // Validate foreign keys if they are being changed
        if (dto.getPacienteId() != null && !dto.getPacienteId().equals(citaExistente.getPacienteId())) {
             pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + dto.getPacienteId()));
        }
        if (dto.getCentroId() != null && !dto.getCentroId().equals(citaExistente.getCentroId())) {
             centroMedicoRepository.findById(dto.getCentroId())
                .orElseThrow(() -> new RuntimeException("Centro Médico no encontrado con ID: " + dto.getCentroId()));
        }
        // Optional: Validate terapeutaId if changed

        citaMapper.updateEntityFromDto(dto, citaExistente); // MapStruct handles update

        Cita actualizada = citaRepository.save(citaExistente);
        return citaMapper.toDTO(actualizada);
    }

    @Transactional
    public void eliminarCita(Integer id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        // Consider related DetalleCitaTratamiento or SesionTerapeutica before deleting
        citaRepository.delete(cita);
    }
}