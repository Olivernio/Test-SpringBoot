package com.acured.profesionales.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acured.common.dto.CurriculumTerapeutaDTO;
import com.acured.profesionales.service.CurriculumTerapeutaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/curriculum") // Nueva ruta base
@RequiredArgsConstructor
public class CurriculumTerapeutaController {

    private final CurriculumTerapeutaService curriculumService;

    @GetMapping
    public ResponseEntity<List<CurriculumTerapeutaDTO>> obtenerTodos() {
        return ResponseEntity.ok(curriculumService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurriculumTerapeutaDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(curriculumService.obtenerPorId(id));
    }

    // Endpoint útil para obtener el CV de un terapeuta específico
    @GetMapping("/terapeuta/{terapeutaId}")
    public ResponseEntity<List<CurriculumTerapeutaDTO>> obtenerPorTerapeutaId(@PathVariable Integer terapeutaId) {
        return ResponseEntity.ok(curriculumService.obtenerPorTerapeutaId(terapeutaId));
    }

    @PostMapping
    public ResponseEntity<CurriculumTerapeutaDTO> crear(@Valid @RequestBody CurriculumTerapeutaDTO dto) {
        CurriculumTerapeutaDTO nuevo = curriculumService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurriculumTerapeutaDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody CurriculumTerapeutaDTO dto) {
        return ResponseEntity.ok(curriculumService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        curriculumService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}