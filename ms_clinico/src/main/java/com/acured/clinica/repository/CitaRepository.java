package com.acured.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acured.clinica.entity.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    // Ejemplo de consulta personalizada:
    // List<Cita> findByPacienteId(Integer pacienteId);
    // List<Cita> findByTerapeutaId(Integer terapeutaId);
}