package com.acured.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acured.clinica.entity.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    // Aquí puedes agregar métodos de consulta personalizados si los necesitas,
    // por ejemplo: List<Paciente> findByUsuarioId(Integer usuarioId);
}