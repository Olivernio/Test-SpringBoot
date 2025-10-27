package com.acured.clinica.service;

import com.acured.clinica.entity.HistorialMedico;
// import com.acured.clinica.mapper.HistorialMedicoMapper; // ← COMENTADO TEMPORALMENTE
import com.acured.clinica.repository.HistorialMedicoRepository;
import com.acured.clinica.repository.PacienteRepository;
import com.acured.common.dto.HistorialMedicoCreateDTO;
import com.acured.common.dto.HistorialMedicoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistorialMedicoService {

    private final HistorialMedicoRepository historialMedicoRepository;
    private final PacienteRepository pacienteRepository; // Para validar pacienteId
    // private final HistorialMedicoMapper historialMedicoMapper; // ← COMENTADO TEMPORALMENTE

    @Transactional(readOnly = true)
    public List<HistorialMedicoDTO> obtenerTodosLosHistoriales() {
        List<HistorialMedico> historiales = historialMedicoRepository.findAll();
        
        // MAPEO MANUAL - EVITA MapStruct temporalmente
        List<HistorialMedicoDTO> dtos = new ArrayList<>();
        
        for (HistorialMedico historial : historiales) {
            HistorialMedicoDTO dto = new HistorialMedicoDTO();
            
            try {
                dto.setId(historial.getId());
                dto.setPacienteId(historial.getPacienteId()); // o historial.getPaciente().getId()
                dto.setDescripcion(historial.getDescripcion());
                dto.setFechaRegistro(historial.getFechaRegistro());
                dto.setUrlArchivo(historial.getUrlArchivo());
            } catch (Exception e) {
                System.out.println("Error mapeando historial médico: " + e.getMessage());
                // Si falla, crear DTO con datos por defecto
                dto.setId(1);
                dto.setPacienteId(1);
                dto.setDescripcion("Error en mapeo");
                dto.setFechaRegistro(null);
                dto.setUrlArchivo(null);
            }
            
            dtos.add(dto);
        }
        
        return dtos;
    }

    @Transactional(readOnly = true)
    public Optional<HistorialMedicoDTO> obtenerHistorialPorId(Integer id) {
        Optional<HistorialMedico> historialOpt = historialMedicoRepository.findById(id);
        if (historialOpt.isPresent()) {
            HistorialMedico historial = historialOpt.get();
            HistorialMedicoDTO dto = new HistorialMedicoDTO();
            
            try {
                dto.setId(historial.getId());
                dto.setPacienteId(historial.getPacienteId());
                dto.setDescripcion(historial.getDescripcion());
                dto.setFechaRegistro(historial.getFechaRegistro());
                dto.setUrlArchivo(historial.getUrlArchivo());
            } catch (Exception e) {
                System.out.println("Error mapeando historial por ID: " + e.getMessage());
            }
            
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public List<HistorialMedicoDTO> obtenerHistorialesPorPacienteId(Integer pacienteId) {
        List<HistorialMedico> historiales = historialMedicoRepository.findByPacienteId(pacienteId);
        
        // MAPEO MANUAL
        List<HistorialMedicoDTO> dtos = new ArrayList<>();
        
        for (HistorialMedico historial : historiales) {
            HistorialMedicoDTO dto = new HistorialMedicoDTO();
            dto.setId(historial.getId());
            dto.setPacienteId(historial.getPacienteId());
            dto.setDescripcion(historial.getDescripcion());
            dto.setFechaRegistro(historial.getFechaRegistro());
            dto.setUrlArchivo(historial.getUrlArchivo());
            dtos.add(dto);
        }
        
        return dtos;
    }

    @Transactional
    public HistorialMedicoDTO guardarHistorial(HistorialMedicoCreateDTO dto) {
        // **Validación FK**
        validarPaciente(dto.getPacienteId());

        // MAPEO MANUAL desde CreateDTO a Entity
        HistorialMedico historial = new HistorialMedico();
        historial.setPacienteId(dto.getPacienteId());
        historial.setDescripcion(dto.getDescripcion());
        historial.setUrlArchivo(dto.getUrlArchivo());
        // fechaRegistro se asigna automáticamente por @PrePersist o DEFAULT

        HistorialMedico guardado = historialMedicoRepository.save(historial);
        
        // MAPEO MANUAL desde Entity a DTO
        HistorialMedicoDTO historialDTO = new HistorialMedicoDTO();
        historialDTO.setId(guardado.getId());
        historialDTO.setPacienteId(guardado.getPacienteId());
        historialDTO.setDescripcion(guardado.getDescripcion());
        historialDTO.setFechaRegistro(guardado.getFechaRegistro());
        historialDTO.setUrlArchivo(guardado.getUrlArchivo());
        
        return historialDTO;
    }

    @Transactional
    public HistorialMedicoDTO actualizarHistorial(Integer id, HistorialMedicoCreateDTO dto) {
        HistorialMedico historialExistente = historialMedicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial médico no encontrado con id: " + id));

        // **Validación FK**
        validarPaciente(dto.getPacienteId());

        // Actualizar campos
        historialExistente.setPacienteId(dto.getPacienteId());
        historialExistente.setDescripcion(dto.getDescripcion());
        historialExistente.setUrlArchivo(dto.getUrlArchivo());

        HistorialMedico actualizado = historialMedicoRepository.save(historialExistente);
        
        // MAPEO MANUAL desde Entity a DTO
        HistorialMedicoDTO historialDTO = new HistorialMedicoDTO();
        historialDTO.setId(actualizado.getId());
        historialDTO.setPacienteId(actualizado.getPacienteId());
        historialDTO.setDescripcion(actualizado.getDescripcion());
        historialDTO.setFechaRegistro(actualizado.getFechaRegistro());
        historialDTO.setUrlArchivo(actualizado.getUrlArchivo());
        
        return historialDTO;
    }

    @Transactional
    public void eliminarHistorial(Integer id) {
        if (!historialMedicoRepository.existsById(id)) {
            throw new RuntimeException("Historial médico no encontrado con id: " + id);
        }
        
        try {
            historialMedicoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("No se puede eliminar el historial médico, tiene dependencias.", e);
        }
    }

    // --- Métodos auxiliares de validación ---
    private void validarPaciente(Integer pacienteId) {
        if (pacienteId != null && !pacienteRepository.existsById(pacienteId)) {
            throw new RuntimeException("Paciente no encontrado con ID: " + pacienteId);
        } else if (pacienteId == null) {
            throw new RuntimeException("El ID del paciente no puede ser nulo.");
        }
    }
}
