package com.acured.clinica.service;

import com.acured.clinica.entity.Cita;
// import com.acured.clinica.mapper.CitaMapper; // ← COMENTADO TEMPORALMENTE
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository; // Para validar pacienteId
    private final CentroMedicoRepository centroMedicoRepository; // Para validar centroId
    // private final CitaMapper citaMapper; // ← COMENTADO TEMPORALMENTE
    // private final UserClient userClient; // Para validar terapeutaId

    @Transactional(readOnly = true)
    public List<CitaDTO> obtenerTodasLasCitas() {
        List<Cita> citas = citaRepository.findAll();
        
        // MAPEO MANUAL - EVITA MapStruct temporalmente
        List<CitaDTO> dtos = new ArrayList<>();
        
        for (Cita cita : citas) {
            CitaDTO dto = new CitaDTO();
            
            try {
                dto.setId(cita.getId());
                dto.setPacienteId(cita.getPacienteId()); // o cita.getPaciente().getId()
                dto.setTerapeutaId(cita.getTerapeutaId()); // o cita.getTerapeuta().getId()
                dto.setCentroId(cita.getCentroId()); // o cita.getCentro().getId()
                dto.setFecha(cita.getFecha());
                dto.setEstado(cita.getEstado());
                dto.setMotivo(cita.getMotivo());
            } catch (Exception e) {
                System.out.println("Error mapeando cita: " + e.getMessage());
                // Si falla, crear DTO con datos por defecto
                dto.setId(1);
                dto.setPacienteId(1);
                dto.setTerapeutaId(2);
                dto.setCentroId(1);
                dto.setEstado("pendiente");
                dto.setMotivo("Error en mapeo");
            }
            
            dtos.add(dto);
        }
        
        return dtos;
    }

    @Transactional(readOnly = true)
    public Optional<CitaDTO> obtenerCitaPorId(Integer id) {
        Optional<Cita> citaOpt = citaRepository.findById(id);
        if (citaOpt.isPresent()) {
            Cita cita = citaOpt.get();
            CitaDTO dto = new CitaDTO();
            
            try {
                dto.setId(cita.getId());
                dto.setPacienteId(cita.getPacienteId());
                dto.setTerapeutaId(cita.getTerapeutaId());
                dto.setCentroId(cita.getCentroId());
                dto.setFecha(cita.getFecha());
                dto.setEstado(cita.getEstado());
                dto.setMotivo(cita.getMotivo());
            } catch (Exception e) {
                System.out.println("Error mapeando cita por ID: " + e.getMessage());
            }
            
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Transactional
    public CitaDTO guardarCita(CitaCreateDTO dto) {
        // **Validaciones FK**
        validarPaciente(dto.getPacienteId());
        validarCentroMedico(dto.getCentroId());
        validarTerapeuta(dto.getTerapeutaId()); // Validaría externamente

        // MAPEO MANUAL desde CreateDTO a Entity
        Cita cita = new Cita();
        cita.setPacienteId(dto.getPacienteId());
        cita.setTerapeutaId(dto.getTerapeutaId());
        cita.setCentroId(dto.getCentroId());
        cita.setFecha(dto.getFecha());
        cita.setEstado("pendiente"); // Estado por defecto
        cita.setMotivo(dto.getMotivo());

        Cita guardada = citaRepository.save(cita);
        
        // MAPEO MANUAL desde Entity a DTO
        CitaDTO citaDTO = new CitaDTO();
        citaDTO.setId(guardada.getId());
        citaDTO.setPacienteId(guardada.getPacienteId());
        citaDTO.setTerapeutaId(guardada.getTerapeutaId());
        citaDTO.setCentroId(guardada.getCentroId());
        citaDTO.setFecha(guardada.getFecha());
        citaDTO.setEstado(guardada.getEstado());
        citaDTO.setMotivo(guardada.getMotivo());
        
        return citaDTO;
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
        
        // MAPEO MANUAL desde Entity a DTO
        CitaDTO citaDTO = new CitaDTO();
        citaDTO.setId(actualizada.getId());
        citaDTO.setPacienteId(actualizada.getPacienteId());
        citaDTO.setTerapeutaId(actualizada.getTerapeutaId());
        citaDTO.setCentroId(actualizada.getCentroId());
        citaDTO.setFecha(actualizada.getFecha());
        citaDTO.setEstado(actualizada.getEstado());
        citaDTO.setMotivo(actualizada.getMotivo());
        
        return citaDTO;
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
