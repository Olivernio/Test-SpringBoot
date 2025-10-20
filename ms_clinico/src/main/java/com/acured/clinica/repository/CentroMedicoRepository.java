package com.acured.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acured.clinica.entity.CentroMedico;

@Repository
public interface CentroMedicoRepository extends JpaRepository<CentroMedico, Integer> {
}