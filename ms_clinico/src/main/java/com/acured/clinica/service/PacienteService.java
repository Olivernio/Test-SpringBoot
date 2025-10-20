package com.acured.clinica.service;

import com.acured.clinica.entity.Paciente;
import com.acured.clinica.mapper.PacienteMapper; // IMPORTANTE
import com.acured.clinica.repository.PacienteRepository;
import com.acured.common.dto.PacienteCreateDTO; // IMPORTANTE
import com.acured.common.dto.PacienteDTO; // IMPORTANTE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; // IMPORTANTE

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteMapper pacienteMapper; // 1. Inyectamos el Mapper

    /**
     * Ahora devuelve una lista de DTOs
     */
    @Transactional(readOnly = true)
    public List<PacienteDTO> obtenerTodosLosPacientes() {
        return pacienteRepository.findAll()
                .stream()
                .map(pacienteMapper::toDTO) // 2. Convertimos cada entidad a DTO
                .collect(Collectors.toList());
    }

    /**
     * Ahora devuelve un DTO
     */
    @Transactional(readOnly = true)
    public Optional<PacienteDTO> obtenerPacientePorId(Integer id) {
        return pacienteRepository.findById(id)
                .map(pacienteMapper::toDTO); // 3. Convertimos la entidad a DTO
    }

    /**
     * Ahora recibe un DTO para crear
     */
    @Transactional
    public PacienteDTO guardarPaciente(PacienteCreateDTO pacienteDTO) {
        // 4. Convertimos el DTO a Entidad
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        
        // 5. Guardamos la Entidad
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        
        // 6. Convertimos la Entidad guardada de nuevo a DTO para la respuesta
        return pacienteMapper.toDTO(pacienteGuardado);
    }

    @Transactional
    public void eliminarPaciente(Integer id) {
        pacienteRepository.deleteById(id);
    }

    /**
     * Ahora recibe un DTO para actualizar
     */
    @Transactional
    public PacienteDTO actualizarPaciente(Integer id, PacienteCreateDTO pacienteActualizadoDTO) {
        Optional<Paciente> pacienteExistenteOpt = pacienteRepository.findById(id);
        
        if (pacienteExistenteOpt.isPresent()) {
            Paciente pacienteExistente = pacienteExistenteOpt.get();
            
            // Actualizamos la entidad con los datos del DTO
            pacienteExistente.setFechaNacimiento(pacienteActualizadoDTO.getFechaNacimiento());
            pacienteExistente.setGenero(pacienteActualizadoDTO.getGenero());
            pacienteExistente.setObservaciones(pacienteActualizadoDTO.getObservaciones());
            pacienteExistente.setUsuarioId(pacienteActualizadoDTO.getUsuarioId());
            
            Paciente pacienteActualizado = pacienteRepository.save(pacienteExistente);
            return pacienteMapper.toDTO(pacienteActualizado);
        } else {
            return null;
        }
    }
}