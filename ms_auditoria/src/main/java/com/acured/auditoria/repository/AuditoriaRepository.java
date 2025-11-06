package com.acured.auditoria.repository;

import com.acured.auditoria.entity.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Integer> {
    
    // Spring Data JPA creará automáticamente los métodos básicos
    // como save(), findById(), findAll(), etc.
    // Para auditoría, usualmente no se necesitan más.
    
}