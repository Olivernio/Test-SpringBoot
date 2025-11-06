package com.acured.auditoria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acured.auditoria.service.AuditoriaService;
import com.acured.common.dto.AuditoriaDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auditoria") // Ruta base para este microservicio
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    /**
     * Endpoint para OBTENER todos los registros de auditoría.
     */
    @GetMapping
    public ResponseEntity<List<AuditoriaDTO>> obtenerTodos() {
        return ResponseEntity.ok(auditoriaService.obtenerTodo());
    }

    /**
     * Endpoint para OBTENER un registro por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaDTO> obtenerPorId(@PathVariable Integer id) {
        // El servicio lanzará una excepción si no lo encuentra
        return ResponseEntity.ok(auditoriaService.obtenerPorId(id));
    }

    /**
     * Endpoint para CREAR un nuevo registro de auditoría.
     * Este será el más utilizado por otros microservicios.
     */
    @PostMapping
    public ResponseEntity<AuditoriaDTO> guardarRegistro(@Valid @RequestBody AuditoriaDTO dto) {
        AuditoriaDTO nuevoRegistro = auditoriaService.guardarRegistro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRegistro);
    }

}