package com.acured.clinica.controller;

import com.acured.common.dto.EspecialidadDTO;
import com.acured.clinica.service.EspecialidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clinico/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    @GetMapping
    public ResponseEntity<List<EspecialidadDTO>> obtenerTodas() {
        return ResponseEntity.ok(especialidadService.obtenerTodasLasEspecialidades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(especialidadService.obtenerEspecialidadPorId(id));
    }

    @PostMapping
    public ResponseEntity<EspecialidadDTO> crearEspecialidad(@Valid @RequestBody EspecialidadDTO dto) {
        EspecialidadDTO nueva = especialidadService.guardarEspecialidad(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> actualizarEspecialidad(@PathVariable Integer id, @Valid @RequestBody EspecialidadDTO dto) {
        return ResponseEntity.ok(especialidadService.actualizarEspecialidad(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEspecialidad(@PathVariable Integer id) {
        especialidadService.eliminarEspecialidad(id);
        return ResponseEntity.noContent().build();
    }
}