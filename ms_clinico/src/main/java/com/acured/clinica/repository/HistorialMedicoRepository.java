package com.acured.clinica.repository;

import com.acured.clinica.entity.HistorialMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Integer> {
    // Ejemplo:
    // List<HistorialMedico> findByPacienteIdOrderByFechaRegistroDesc(Integer pacienteId);
}