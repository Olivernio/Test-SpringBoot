package com.acured.clinica.controller;

import com.acured.common.dto.HistorialMedicoDTO;
import com.acured.clinica.service.HistorialMedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clinico/historiales")
@RequiredArgsConstructor
public class HistorialMedicoController {

    private final HistorialMedicoService historialService;

    @GetMapping
    public ResponseEntity<List<HistorialMedicoDTO>> obtenerTodos() {
        return ResponseEntity.ok(historialService.obtenerTodoElHistorial());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialMedicoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(historialService.obtenerHistorialPorId(id));
    }

    @PostMapping
    public ResponseEntity<HistorialMedicoDTO> crearHistorial(@Valid @RequestBody HistorialMedicoDTO dto) {
        // Consider try-catch
        HistorialMedicoDTO nuevo = historialService.guardarHistorial(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedicoDTO> actualizarHistorial(@PathVariable Integer id, @Valid @RequestBody HistorialMedicoDTO dto) {
        // Consider try-catch
        return ResponseEntity.ok(historialService.actualizarHistorial(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHistorial(@PathVariable Integer id) {
        historialService.eliminarHistorial(id);
        return ResponseEntity.noContent().build();
    }
}