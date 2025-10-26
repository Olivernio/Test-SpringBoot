package com.acured.clinica.controller;

import com.acured.common.dto.CitaCreateDTO;
import com.acured.common.dto.CitaDTO;
import com.acured.clinica.service.CitaService;
import jakarta.validation.Valid; // Necesario
import lombok.RequiredArgsConstructor; // Necesario
// import org.springframework.beans.factory.annotation.Autowired; // Eliminado
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinico/citas")
@RequiredArgsConstructor // <--- Reemplaza @Autowired
public class CitaController {

    // @Autowired // Eliminado
    private final CitaService citaService; // <--- Declarado como final

    @GetMapping
    public ResponseEntity<List<CitaDTO>> obtenerTodas() {
        return ResponseEntity.ok(citaService.obtenerTodasLasCitas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> obtenerPorId(@PathVariable Integer id) {
        return citaService.obtenerCitaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CitaDTO> crearCita(@RequestBody @Valid CitaCreateDTO dto) { // <--- Añadido @Valid
        // Manejo de RuntimeException (ej: paciente no encontrado) se puede hacer globalmente
        CitaDTO nuevaCita = citaService.guardarCita(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
        /* Código anterior con try-catch local:
        try {
            CitaDTO nuevaCita = citaService.guardarCita(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
        } catch (RuntimeException e) {
            // Captura errores si el paciente o centro no se encuentran
            return ResponseEntity.badRequest().build(); // Podría ser mejor un 404 si es por ID no encontrado
        }
        */
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDTO> actualizarCita(@PathVariable Integer id, @RequestBody @Valid CitaCreateDTO dto) { // <--- Añadido @Valid
        // Asumiendo que el servicio lanza excepción si el ID no existe o si las FK no son válidas
        try {
            CitaDTO actualizada = citaService.actualizarCita(id, dto);
            return ResponseEntity.ok(actualizada); // Devuelve 200 OK si actualiza
        } catch (RuntimeException e) { // Captura si ID no existe o FK inválida
            // Considerar manejo global o devolver 404/400 según el error
             return ResponseEntity.notFound().build(); // Simplificado a 404 si algo no se encuentra
        }
         /* Código anterior:
         try {
             CitaDTO actualizada = citaService.actualizarCita(id, dto);
             if (actualizada != null) { // Servicio devolvía null
                 return ResponseEntity.ok(actualizada);
             } else {
                 return ResponseEntity.notFound().build();
             }
         } catch (RuntimeException e) { // Capturaba FK inválida
             return ResponseEntity.badRequest().build();
         }
        */
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Integer id) {
        // Asumiendo que el servicio valida la existencia
        try {
            citaService.eliminarCita(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) { // Ajusta el tipo de excepción
             return ResponseEntity.notFound().build();
        }
        /* Código anterior:
        if (citaService.obtenerCitaPorId(id).isPresent()) {
            citaService.eliminarCita(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
        */
    }
}