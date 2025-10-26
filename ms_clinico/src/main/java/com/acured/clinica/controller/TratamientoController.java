package com.acured.clinica.controller;

import com.acured.common.dto.TratamientoDTO;
import com.acured.clinica.service.TratamientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clinico/tratamientos")
@RequiredArgsConstructor
public class TratamientoController {

    private final TratamientoService tratamientoService;

    @GetMapping
    public ResponseEntity<List<TratamientoDTO>> obtenerTodos() {
        return ResponseEntity.ok(tratamientoService.obtenerTodosLosTratamientos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TratamientoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(tratamientoService.obtenerTratamientoPorId(id));
    }

    @PostMapping
    public ResponseEntity<TratamientoDTO> crearTratamiento(@Valid @RequestBody TratamientoDTO dto) {
        TratamientoDTO nuevo = tratamientoService.guardarTratamiento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TratamientoDTO> actualizarTratamiento(@PathVariable Integer id, @Valid @RequestBody TratamientoDTO dto) {
        return ResponseEntity.ok(tratamientoService.actualizarTratamiento(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTratamiento(@PathVariable Integer id) {
        tratamientoService.eliminarTratamiento(id);
        return ResponseEntity.noContent().build();
    }
}