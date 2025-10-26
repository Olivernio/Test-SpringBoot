package com.acured.clinica.controller;

import com.acured.common.dto.CentroMedicoDTO;
import com.acured.clinica.service.CentroMedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clinico/centros-medicos") // Changed path for clarity
public class CentroMedicoController {

    private final CentroMedicoService centroMedicoService;

    @GetMapping
    public ResponseEntity<List<CentroMedicoDTO>> obtenerTodos() {
        return ResponseEntity.ok(centroMedicoService.obtenerTodosLosCentros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentroMedicoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(centroMedicoService.obtenerCentroPorId(id));
    }

    @PostMapping
    public ResponseEntity<CentroMedicoDTO> crearCentro(@Valid @RequestBody CentroMedicoDTO dto) {
        CentroMedicoDTO nuevo = centroMedicoService.guardarCentro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentroMedicoDTO> actualizarCentro(@PathVariable Integer id, @Valid @RequestBody CentroMedicoDTO dto) {
        return ResponseEntity.ok(centroMedicoService.actualizarCentro(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCentro(@PathVariable Integer id) {
        centroMedicoService.eliminarCentro(id);
        return ResponseEntity.noContent().build();
    }
}