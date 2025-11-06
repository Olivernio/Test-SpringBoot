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

import com.acured.common.dto.CertificacionDTO;
import com.acured.profesionales.service.CertificacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profesionales/certificaciones") // Nueva ruta base
@RequiredArgsConstructor
public class CertificacionController {

    private final CertificacionService certificacionService;

    @GetMapping
    public ResponseEntity<List<CertificacionDTO>> obtenerTodas() {
        return ResponseEntity.ok(certificacionService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificacionDTO> obtenerPorId(@PathVariable Integer id) {
        // El servicio lanzará excepción si no lo encuentra
        return ResponseEntity.ok(certificacionService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CertificacionDTO> crear(@Valid @RequestBody CertificacionDTO dto) {
        CertificacionDTO nueva = certificacionService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificacionDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody CertificacionDTO dto) {
        // El servicio lanzará excepción si no lo encuentra
        return ResponseEntity.ok(certificacionService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        // El servicio lanzará excepción si no lo encuentra
        certificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}