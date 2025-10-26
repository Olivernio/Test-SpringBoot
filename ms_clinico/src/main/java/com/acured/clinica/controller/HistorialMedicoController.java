package com.acured.clinica.controller;

import com.acured.common.dto.HistorialMedicoCreateDTO;
import com.acured.common.dto.HistorialMedicoDTO;
import com.acured.clinica.service.HistorialMedicoService;
import jakarta.validation.Valid; // Necesario
import lombok.RequiredArgsConstructor; // Necesario
// import org.springframework.beans.factory.annotation.Autowired; // Eliminado
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinico/historiales")
@RequiredArgsConstructor // <--- Reemplaza @Autowired
public class HistorialMedicoController {

    // @Autowired // Eliminado
    private final HistorialMedicoService historialService; // <--- Declarado como final

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
    public ResponseEntity<HistorialMedicoDTO> crearHistorial(@RequestBody @Valid HistorialMedicoCreateDTO dto) { // <--- Añadido @Valid
        // Asumiendo manejo global de excepciones si el pacienteId no existe
        HistorialMedicoDTO nuevo = historialService.guardarHistorial(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        /* Código anterior:
        try {
            HistorialMedicoDTO nuevo = historialService.guardarHistorial(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // Si el paciente no existe (mejor 404 o 400 específico)
        }
        */
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedicoDTO> actualizarHistorial(@PathVariable Integer id, @RequestBody @Valid HistorialMedicoCreateDTO dto) { // <--- Añadido @Valid
        // Asumiendo manejo global de excepciones
         try {
            HistorialMedicoDTO actualizado = historialService.actualizarHistorial(id, dto);
             return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) { // Captura si ID no existe o pacienteId inválido
             return ResponseEntity.notFound().build(); // Simplificado a 404
        }
        /* Código anterior:
        try {
            HistorialMedicoDTO actualizado = historialService.actualizarHistorial(id, dto);
            if (actualizado != null) { // Servicio devolvía null
                return ResponseEntity.ok(actualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) { // Capturaba pacienteId inválido
            return ResponseEntity.badRequest().build();
        }
        */
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHistorial(@PathVariable Integer id) {
        // Asumiendo que el servicio valida la existencia
         try {
            historialService.eliminarHistorial(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) { // Ajusta el tipo de excepción
             return ResponseEntity.notFound().build();
        }
        /* Código anterior:
        if (historialService.obtenerHistorialPorId(id).isPresent()) {
            historialService.eliminarHistorial(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
        */
    }
}