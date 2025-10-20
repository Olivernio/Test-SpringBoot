package com.acured.clinica.service;

import com.acured.clinica.entity.Cita;
import com.acured.clinica.entity.DetalleCitaTratamiento;
import com.acured.clinica.entity.SesionTerapeutica;
import com.acured.clinica.repository.CitaRepository;
import com.acured.clinica.repository.DetalleCitaTratamientoRepository;
import com.acured.clinica.repository.SesionTerapeuticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private SesionTerapeuticaRepository sesionRepository;

    @Autowired
    private DetalleCitaTratamientoRepository detalleRepository;

    // --- Métodos de Cita ---

    @Transactional(readOnly = true)
    public List<Cita> obtenerTodasLasCitas() {
        return citaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Cita> obtenerCitaPorId(Integer id) {
        return citaRepository.findById(id);
    }

    @Transactional
    public Cita guardarCita(Cita cita) {
        // La lógica de @PrePersist en la entidad Cita se encargará
        // de poner el estado 'pendiente' si viene nulo.
        return citaRepository.save(cita);
    }

    @Transactional
    public Cita actualizarCita(Integer id, Cita citaActualizada) {
        Optional<Cita> citaExistenteOpt = citaRepository.findById(id);
        
        if (citaExistenteOpt.isPresent()) {
            Cita citaExistente = citaExistenteOpt.get();
            
            citaExistente.setPaciente(citaActualizada.getPaciente());
            citaExistente.setTerapeutaId(citaActualizada.getTerapeutaId());
            citaExistente.setCentroMedico(citaActualizada.getCentroMedico());
            citaExistente.setFecha(citaActualizada.getFecha());
            citaExistente.setEstado(citaActualizada.getEstado());
            citaExistente.setMotivo(citaActualizada.getMotivo());
            
            return citaRepository.save(citaExistente);
        } else {
            // Considera lanzar una excepción aquí (ej. ResourceNotFoundException)
            return null;
        }
    }

    @Transactional
    public void eliminarCita(Integer id) {
        // Esto eliminará la cita y, gracias a `cascade = CascadeType.ALL`
        // en la entidad Cita, también eliminará sus detalles y sesiones asociadas.
        citaRepository.deleteById(id);
    }

    // --- Métodos para entidades relacionadas (ejemplos) ---

    @Transactional
    public SesionTerapeutica agregarSesionACita(Integer citaId, SesionTerapeutica sesion) {
        Optional<Cita> citaOpt = citaRepository.findById(citaId);
        if (citaOpt.isPresent()) {
            sesion.setCita(citaOpt.get());
            return sesionRepository.save(sesion);
        } else {
            return null; // O lanzar excepción
        }
    }

    @Transactional
    public DetalleCitaTratamiento agregarTratamientoACita(Integer citaId, DetalleCitaTratamiento detalle) {
        Optional<Cita> citaOpt = citaRepository.findById(citaId);
        if (citaOpt.isPresent()) {
            detalle.setCita(citaOpt.get());
            return detalleRepository.save(detalle);
        } else {
            return null; // O lanzar excepción
        }
    }
}