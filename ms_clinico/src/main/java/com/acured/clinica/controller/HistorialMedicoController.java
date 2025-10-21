package com.acured.clinica.controller;

import com.acured.common.dto.HistorialMedicoCreateDTO;
import com.acured.common.dto.HistorialMedicoDTO;
import com.acured.clinica.service.HistorialMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinico/historiales")
public class HistorialMedicoController {

    @Autowired
    private HistorialMedicoService historialService;

    @GetMapping
    public ResponseEntity<List<HistorialMedicoDTO>> obtenerTodos() {
        return ResponseEntity.ok(historialService.obtenerTodoElHistorial());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialMedicoDTO> obtenerPorId(@PathVariable Integer id) {
        return historialService.obtenerHistorialPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HistorialMedicoDTO> crearHistorial(@RequestBody HistorialMedicoCreateDTO dto) {
        try {
            HistorialMedicoDTO nuevo = historialService.guardarHistorial(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // Si el paciente no existe
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedicoDTO> actualizarHistorial(@PathVariable Integer id, @RequestBody HistorialMedicoCreateDTO dto) {
        try {
            HistorialMedicoDTO actualizado = historialService.actualizarHistorial(id, dto);
            if (actualizado != null) {
                return ResponseEntity.ok(actualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHistorial(@PathVariable Integer id) {
        if (historialService.obtenerHistorialPorId(id).isPresent()) {
            historialService.eliminarHistorial(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}