package com.acured.clinica.service;

import com.acured.clinica.entity.CentroMedico;
import com.acured.clinica.repository.CentroMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CentroMedicoService {

    @Autowired
    private CentroMedicoRepository centroMedicoRepository;

    @Transactional(readOnly = true)
    public List<CentroMedico> obtenerTodosLosCentros() {
        return centroMedicoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<CentroMedico> obtenerCentroPorId(Integer id) {
        return centroMedicoRepository.findById(id);
    }

    @Transactional
    public CentroMedico guardarCentro(CentroMedico centroMedico) {
        return centroMedicoRepository.save(centroMedico);
    }

    @Transactional
    public CentroMedico actualizarCentro(Integer id, CentroMedico centroActualizado) {
        Optional<CentroMedico> existenteOpt = centroMedicoRepository.findById(id);
        
        if (existenteOpt.isPresent()) {
            CentroMedico existente = existenteOpt.get();
            existente.setNombre(centroActualizado.getNombre());
            existente.setDireccion(centroActualizado.getDireccion());
            existente.setTelefono(centroActualizado.getTelefono());
            existente.setEmail(centroActualizado.getEmail());
            existente.setSitioWeb(centroActualizado.getSitioWeb());
            existente.setPaisId(centroActualizado.getPaisId());
            
            return centroMedicoRepository.save(existente);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarCentro(Integer id) {
        centroMedicoRepository.deleteById(id);
    }
    
    // Aquí podrías agregar lógica para manejar ServicioCentro si lo necesitas
}