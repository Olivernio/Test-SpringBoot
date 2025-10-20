package com.acured.clinica.service;

import com.acured.clinica.entity.Especialidad;
import com.acured.clinica.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional(readOnly = true)
    public List<Especialidad> obtenerTodasLasEspecialidades() {
        return especialidadRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Especialidad> obtenerEspecialidadPorId(Integer id) {
        return especialidadRepository.findById(id);
    }

    @Transactional
    public Especialidad guardarEspecialidad(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    @Transactional
    public Especialidad actualizarEspecialidad(Integer id, Especialidad especialidadActualizada) {
        Optional<Especialidad> existenteOpt = especialidadRepository.findById(id);
        
        if (existenteOpt.isPresent()) {
            Especialidad existente = existenteOpt.get();
            existente.setNombre(especialidadActualizada.getNombre());
            
            return especialidadRepository.save(existente);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarEspecialidad(Integer id) {
        especialidadRepository.deleteById(id);
    }
}