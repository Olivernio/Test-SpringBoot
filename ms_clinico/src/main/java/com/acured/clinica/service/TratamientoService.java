package com.acured.clinica.service;

import com.acured.clinica.entity.Tratamiento;
import com.acured.clinica.repository.TratamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TratamientoService {

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Transactional(readOnly = true)
    public List<Tratamiento> obtenerTodosLosTratamientos() {
        return tratamientoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Tratamiento> obtenerTratamientoPorId(Integer id) {
        return tratamientoRepository.findById(id);
    }

    @Transactional
    public Tratamiento guardarTratamiento(Tratamiento tratamiento) {
        return tratamientoRepository.save(tratamiento);
    }

    @Transactional
    public Tratamiento actualizarTratamiento(Integer id, Tratamiento tratamientoActualizado) {
        Optional<Tratamiento> existenteOpt = tratamientoRepository.findById(id);
        
        if (existenteOpt.isPresent()) {
            Tratamiento existente = existenteOpt.get();
            existente.setNombre(tratamientoActualizado.getNombre());
            existente.setDescripcion(tratamientoActualizado.getDescripcion());
            existente.setDuracionMin(tratamientoActualizado.getDuracionMin());
            existente.setPrecio(tratamientoActualizado.getPrecio());
            existente.setEspecialidad(tratamientoActualizado.getEspecialidad());
            
            return tratamientoRepository.save(existente);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarTratamiento(Integer id) {
        tratamientoRepository.deleteById(id);
    }
}