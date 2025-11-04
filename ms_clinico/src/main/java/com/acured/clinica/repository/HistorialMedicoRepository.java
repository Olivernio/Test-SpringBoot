package com.acured.clinica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acured.clinica.entity.HistorialMedico;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Integer> {
    // Ejemplo:
    // List<HistorialMedico> findByPacienteIdOrderByFechaRegistroDesc(Integer pacienteId);
    List<HistorialMedico> findByPacienteId(Integer pacienteId);
}