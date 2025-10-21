package com.acured.clinica.service;

import com.acured.clinica.entity.HistorialMedico;
import com.acured.clinica.entity.Paciente;
import com.acured.clinica.mapper.HistorialMedicoMapper;
import com.acured.clinica.repository.HistorialMedicoRepository;
import com.acured.clinica.repository.PacienteRepository;
import com.acured.common.dto.HistorialMedicoCreateDTO;
import com.acured.common.dto.HistorialMedicoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistorialMedicoService {

    @Autowired
    private HistorialMedicoRepository historialRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private HistorialMedicoMapper historialMapper;

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
        HistorialMedico historial = historialMapper.toEntity(dto);

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        historial.setPaciente(paciente);
        
        HistorialMedico guardado = historialRepository.save(historial);
        return historialMapper.toDTO(guardado);
    }

    @Transactional
    public HistorialMedicoDTO actualizarHistorial(Integer id, HistorialMedicoCreateDTO dto) {
        Optional<HistorialMedico> existenteOpt = historialRepository.findById(id);
        
        if (existenteOpt.isPresent()) {
            HistorialMedico existente = existenteOpt.get();
            
            Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
            existente.setPaciente(paciente);
            existente.setDescripcion(dto.getDescripcion());
            existente.setUrlArchivo(dto.getUrlArchivo());
            
            HistorialMedico actualizado = historialRepository.save(existente);
            return historialMapper.toDTO(actualizado);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarHistorial(Integer id) {
        historialRepository.deleteById(id);
    }
}