package com.acured.clinica.service;

import com.acured.clinica.entity.Cita;
import com.acured.clinica.entity.CentroMedico;
import com.acured.clinica.entity.Paciente;
import com.acured.clinica.mapper.CitaMapper;
import com.acured.clinica.repository.CitaRepository;
import com.acured.clinica.repository.CentroMedicoRepository;
import com.acured.clinica.repository.PacienteRepository;
import com.acured.common.dto.CitaCreateDTO;
import com.acured.common.dto.CitaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private CentroMedicoRepository centroMedicoRepository;

    @Autowired
    private CitaMapper citaMapper;
    
    // NOTA: Inyecta SesionTerapeuticaRepository y DetalleCitaTratamientoRepository
    // si mantienes los métodos para agregar sesiones/detalles.

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
        Cita cita = citaMapper.toEntity(dto);

        // Asignamos Paciente y Centro desde los IDs
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        CentroMedico centro = centroMedicoRepository.findById(dto.getCentroId())
                .orElseThrow(() -> new RuntimeException("Centro médico no encontrado"));
        
        cita.setPaciente(paciente);
        cita.setCentroMedico(centro);
        
        Cita guardada = citaRepository.save(cita);
        return citaMapper.toDTO(guardada);
    }

    @Transactional
    public CitaDTO actualizarCita(Integer id, CitaCreateDTO dto) {
        Optional<Cita> citaExistenteOpt = citaRepository.findById(id);
        
        if (citaExistenteOpt.isPresent()) {
            Cita citaExistente = citaExistenteOpt.get();
            
            // Asignamos Paciente y Centro desde los IDs
            Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
            CentroMedico centro = centroMedicoRepository.findById(dto.getCentroId())
                    .orElseThrow(() -> new RuntimeException("Centro médico no encontrado"));

            citaExistente.setPaciente(paciente);
            citaExistente.setTerapeutaId(dto.getTerapeutaId());
            citaExistente.setCentroMedico(centro);
            citaExistente.setFecha(dto.getFecha());
            citaExistente.setMotivo(dto.getMotivo());
            // Opcional: permitir actualizar estado
            // citaExistente.setEstado(dto.getEstado()); 
            
            Cita actualizada = citaRepository.save(citaExistente);
            return citaMapper.toDTO(actualizada);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarCita(Integer id) {
        citaRepository.deleteById(id);
    }

    // ... (Puedes mantener los métodos de agregarSesion/Detalle aquí,
    // pero necesitarán ser actualizados para usar DTOs también)
}