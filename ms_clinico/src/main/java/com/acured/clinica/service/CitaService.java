package com.acured.clinica.service;

import com.acured.clinica.entity.Cita;
import com.acured.clinica.mapper.CitaMapper;
import com.acured.clinica.repository.CitaRepository;
import com.acured.clinica.repository.CentroMedicoRepository;
import com.acured.clinica.repository.PacienteRepository;
// Importar Cliente si validas terapeutaId
// import com.acured.clinica.client.UserClient;
import com.acured.common.dto.CitaCreateDTO;
import com.acured.common.dto.CitaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException; // Para eliminar
import lombok.Getter; // <--- Make sure this is imported
import lombok.Setter; // <--- Make sure this is imported

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository; // Para validar pacienteId
    private final CentroMedicoRepository centroMedicoRepository; // Para validar centroId
    private final CitaMapper citaMapper;
    // private final UserClient userClient; // Para validar terapeutaId

    @Transactional(readOnly = true)
    public List<CitaDTO> obtenerTodasLasCitas() {
        return citaRepository.findAll().stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<CitaDTO> obtenerCitaPorId(Integer id) {
        return citaRepository.findById(id)
                .map(citaMapper::toDTO);
    }

    @Transactional
    public CitaDTO guardarCita(CitaCreateDTO dto) {
        // **Validaciones FK**
        validarPaciente(dto.getPacienteId());
        validarCentroMedico(dto.getCentroId());
        validarTerapeuta(dto.getTerapeutaId()); // Validaría externamente

        Cita cita = citaMapper.toEntity(dto);
        // El estado 'pendiente' y la fecha se manejan con @PrePersist o BD DEFAULT

        Cita guardada = citaRepository.save(cita);
        return citaMapper.toDTO(guardada);
    }

    @Transactional
    public CitaDTO actualizarCita(Integer id, CitaCreateDTO dto) {
        Cita citaExistente = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));

        // **Validaciones FK**
        validarPaciente(dto.getPacienteId());
        validarCentroMedico(dto.getCentroId());
        validarTerapeuta(dto.getTerapeutaId());

        // Actualizar campos
        citaExistente.setPacienteId(dto.getPacienteId());
        citaExistente.setTerapeutaId(dto.getTerapeutaId());
        citaExistente.setCentroId(dto.getCentroId());
        citaExistente.setFecha(dto.getFecha());
        citaExistente.setMotivo(dto.getMotivo());
        // Podrías añadir lógica para actualizar el 'estado' si es necesario

        Cita actualizada = citaRepository.save(citaExistente);
        return citaMapper.toDTO(actualizada);
    }

    @Transactional
    public void eliminarCita(Integer id) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con id: " + id);
        }
        // El SQL tiene ON DELETE CASCADE para detalle_cita_tratamiento y sesion_terapeutica
        // Debería borrar en cascada sin problemas. Capturamos por si acaso.
         try {
            citaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // No debería ocurrir por el CASCADE
             throw new RuntimeException("No se puede eliminar la cita, tiene dependencias.", e);
        }
    }

    // --- Métodos auxiliares de validación ---
    private void validarPaciente(Integer pacienteId) {
        if (pacienteId != null && !pacienteRepository.existsById(pacienteId)) {
            throw new RuntimeException("Paciente no encontrado con ID: " + pacienteId);
        } else if (pacienteId == null) {
             throw new RuntimeException("El ID del paciente no puede ser nulo."); // Si es obligatorio
        }
    }

    private void validarCentroMedico(Integer centroId) {
        if (centroId != null && !centroMedicoRepository.existsById(centroId)) {
            throw new RuntimeException("Centro médico no encontrado con ID: " + centroId);
        } else if (centroId == null) {
             throw new RuntimeException("El ID del centro médico no puede ser nulo."); // Si es obligatorio
        }
    }

     private void validarTerapeuta(Integer terapeutaId) {
         if (terapeutaId != null) {
            // Lógica de validación externa (Feign Client)
            // try { userClient.findById(terapeutaId); } catch (FeignException.NotFound e) { ... }
             System.out.println("ADVERTENCIA: Validación de terapeutaId no implementada."); // Placeholder
         } else {
              throw new RuntimeException("El ID del terapeuta no puede ser nulo."); // Si es obligatorio
         }
     }
}