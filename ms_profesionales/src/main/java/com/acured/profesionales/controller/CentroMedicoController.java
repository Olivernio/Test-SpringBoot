package com.acured.profesionales.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping; // Necesario
import org.springframework.web.bind.annotation.GetMapping; // Necesario
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acured.common.dto.CentroMedicoCreateDTO;
import com.acured.common.dto.CentroMedicoDTO;
import com.acured.profesionales.service.CentroMedicoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profesionales/centros-medicos")
@RequiredArgsConstructor // <--- Reemplaza @Autowired
public class CentroMedicoController {

    // @Autowired // Eliminado
    private final CentroMedicoService centroMedicoService; // <--- Declarado como final

    @GetMapping
    public ResponseEntity<List<CentroMedicoDTO>> obtenerTodos() {
        return ResponseEntity.ok(centroMedicoService.obtenerTodosLosCentros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentroMedicoDTO> obtenerPorId(@PathVariable Integer id) {
        return centroMedicoService.obtenerCentroPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CentroMedicoDTO> crearCentro(@RequestBody @Valid CentroMedicoCreateDTO dto) { // <--- Añadido @Valid (y quitado el @Valid que estaba mal puesto antes)
        CentroMedicoDTO nuevo = centroMedicoService.guardarCentro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentroMedicoDTO> actualizarCentro(@PathVariable Integer id, @RequestBody @Valid CentroMedicoCreateDTO dto) { // <--- Añadido @Valid
        CentroMedicoDTO actualizado = centroMedicoService.actualizarCentro(id, dto);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCentro(@PathVariable Integer id) {
        // Asumiendo que el servicio valida la existencia
        try {
            centroMedicoService.eliminarCentro(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) { // Ajusta el tipo de excepción si usas una custom
             return ResponseEntity.notFound().build();
        }
        /* Código anterior:
        if (centroMedicoService.obtenerCentroPorId(id).isPresent()) {
            centroMedicoService.eliminarCentro(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
        */
    }
}