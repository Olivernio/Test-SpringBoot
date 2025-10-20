package com.acured.clinica.controller;

import com.acured.clinica.entity.CentroMedico;
import com.acured.clinica.service.CentroMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinico/centros-medicos")
public class CentroMedicoController {

    @Autowired
    private CentroMedicoService centroMedicoService;

    @GetMapping
    public ResponseEntity<List<CentroMedico>> obtenerTodos() {
        return ResponseEntity.ok(centroMedicoService.obtenerTodosLosCentros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentroMedico> obtenerPorId(@PathVariable Integer id) {
        return centroMedicoService.obtenerCentroPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CentroMedico> crearCentro(@RequestBody CentroMedico centroMedico) {
        CentroMedico nuevo = centroMedicoService.guardarCentro(centroMedico);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentroMedico> actualizarCentro(@PathVariable Integer id, @RequestBody CentroMedico centroMedico) {
        CentroMedico actualizado = centroMedicoService.actualizarCentro(id, centroMedico);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCentro(@PathVariable Integer id) {
        if (centroMedicoService.obtenerCentroPorId(id).isPresent()) {
            centroMedicoService.eliminarCentro(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}