package com.acured.clinica.controller; // Asegúrate que el package sea 'controller'

import com.acured.common.dto.EspecialidadCreateDTO;
import com.acured.common.dto.EspecialidadDTO;
import com.acured.clinica.service.EspecialidadService; // Importa el servicio correcto
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException; // Import necesario si manejas la excepción aquí

import java.util.List;

@RestController
@RequestMapping("/api/clinico/especialidades")
@RequiredArgsConstructor
public class EspecialidadController { // Esta es la única clase que debe estar en este archivo

    private final EspecialidadService especialidadService; // Inyecta el servicio

    @GetMapping
    public ResponseEntity<List<EspecialidadDTO>> obtenerTodas() {
        return ResponseEntity.ok(especialidadService.obtenerTodasLasEspecialidades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> obtenerPorId(@PathVariable Integer id) {
        return especialidadService.obtenerEspecialidadPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EspecialidadDTO> crearEspecialidad(@RequestBody @Valid EspecialidadCreateDTO dto) {
        try {
            EspecialidadDTO nueva = especialidadService.guardarEspecialidad(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) { // Captura error de nombre duplicado del servicio
            // Considera manejo global para devolver 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // O .badRequest() genérico
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> actualizarEspecialidad(@PathVariable Integer id, @RequestBody @Valid EspecialidadCreateDTO dto) {
         try {
            EspecialidadDTO actualizada = especialidadService.actualizarEspecialidad(id, dto);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) { // Captura ID no encontrado o nombre duplicado
            // Considera manejo global
            if (e.getMessage() != null && e.getMessage().contains("ya está en uso")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
            }
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEspecialidad(@PathVariable Integer id) {
         try {
            especialidadService.eliminarEspecialidad(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) { // Captura ID no encontrado o error de integridad
            // Considera manejo global
            if (e.getCause() instanceof DataIntegrityViolationException || (e.getMessage() != null && e.getMessage().contains("en uso"))) {
                 return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict si está en uso
            }
            return ResponseEntity.notFound().build(); // 404 si no se encontró
        }
    }

    // --- EL CÓDIGO DE EspecialidadService YA NO ESTÁ AQUÍ ---

} // Fin de la clase EspecialidadController