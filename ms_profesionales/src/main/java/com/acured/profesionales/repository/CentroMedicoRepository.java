package com.acured.profesionales.repository; // Paquete correcto (profesionales)

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acured.profesionales.entity.CentroMedico; 

@Repository
public interface CentroMedicoRepository extends JpaRepository<CentroMedico, Integer> {
}