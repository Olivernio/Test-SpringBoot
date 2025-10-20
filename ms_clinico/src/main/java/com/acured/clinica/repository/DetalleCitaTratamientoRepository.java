package com.acured.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acured.clinica.entity.DetalleCitaTratamiento;

@Repository
public interface DetalleCitaTratamientoRepository extends JpaRepository<DetalleCitaTratamiento, Integer> {
    // Ejemplo:
    // List<DetalleCitaTratamiento> findByCitaId(Integer citaId);
}