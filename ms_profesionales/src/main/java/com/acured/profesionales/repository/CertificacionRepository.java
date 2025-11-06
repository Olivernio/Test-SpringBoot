package com.acured.profesionales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acured.profesionales.entity.Certificacion;

@Repository
public interface CertificacionRepository extends JpaRepository<Certificacion, Integer> {
    // Spring Data JPA crea automáticamente los métodos findById, findAll, save, delete, etc.
}