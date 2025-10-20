package com.acured.clinica.controller;

import com.acured.clinica.entity.HistorialMedico;
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
    public ResponseEntity<List<HistorialMedico>> obtenerTodos() {
        return ResponseEntity.ok(historialService.obtenerTodoElHistorial());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialMedico> obtenerPorId(@PathVariable Integer id) {
        return historialService.obtenerHistorialPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HistorialMedico> crearHistorial(@RequestBody HistorialMedico historial) {
        HistorialMedico nuevo = historialService.guardarHistorial(historial);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedico> actualizarHistorial(@PathVariable Integer id, @RequestBody HistorialMedico historial) {
        HistorialMedico actualizado = historialService.actualizarHistorial(id, historial);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
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