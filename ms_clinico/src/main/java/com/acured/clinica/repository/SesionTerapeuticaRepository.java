package com.acured.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acured.clinica.entity.SesionTerapeutica;

@Repository
public interface SesionTerapeuticaRepository extends JpaRepository<SesionTerapeutica, Integer> {
    // Ejemplo:
    // List<SesionTerapeutica> findByCitaId(Integer citaId);
}