package com.acured.clinica.service;

import com.acured.clinica.entity.HistorialMedico;
import com.acured.clinica.repository.HistorialMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialMedicoService {

    @Autowired
    private HistorialMedicoRepository historialRepository;

    @Transactional(readOnly = true)
    public List<HistorialMedico> obtenerTodoElHistorial() {
        return historialRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<HistorialMedico> obtenerHistorialPorId(Integer id) {
        return historialRepository.findById(id);
    }

    @Transactional
    public HistorialMedico guardarHistorial(HistorialMedico historialMedico) {
        // La entidad se encarga de la fecha de registro vía @PrePersist
        return historialRepository.save(historialMedico);
    }

    @Transactional
    public HistorialMedico actualizarHistorial(Integer id, HistorialMedico historialActualizado) {
        Optional<HistorialMedico> existenteOpt = historialRepository.findById(id);
        
        if (existenteOpt.isPresent()) {
            HistorialMedico existente = existenteOpt.get();
            existente.setPaciente(historialActualizado.getPaciente());
            existente.setDescripcion(historialActualizado.getDescripcion());
            existente.setUrlArchivo(historialActualizado.getUrlArchivo());
            // No actualizamos la fecha de registro
            
            return historialRepository.save(existente);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarHistorial(Integer id) {
        historialRepository.deleteById(id);
    }
}