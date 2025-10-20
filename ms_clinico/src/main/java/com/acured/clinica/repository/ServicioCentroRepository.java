package com.acured.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acured.clinica.entity.ServicioCentro;

@Repository
public interface ServicioCentroRepository extends JpaRepository<ServicioCentro, Integer> {
    // Ejemplo:
    // List<ServicioCentro> findByCentroMedicoId(Integer centroId);
    // List<ServicioCentro> findByTratamientoId(Integer tratamientoId);
}