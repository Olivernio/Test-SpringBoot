package com.acured.profesionales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acured.profesionales.entity.CurriculumTerapeuta;

@Repository
public interface CurriculumTerapeutaRepository extends JpaRepository<CurriculumTerapeuta, Integer> {
    
    // Este es un método de consulta personalizado muy útil.
    // Spring Data JPA lo entenderá automáticamente por el nombre.
    // Nos permitirá buscar todos los items del currículum para un terapeuta específico.
    List<CurriculumTerapeuta> findByTerapeutaId(Integer terapeutaId);
}