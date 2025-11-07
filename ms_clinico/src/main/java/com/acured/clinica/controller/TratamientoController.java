package com.acured.clinica.controller; // Asegúrate que el package sea 'controller'

import com.acured.common.dto.TratamientoCreateDTO;
import com.acured.common.dto.TratamientoDTO;
import com.acured.clinica.service.TratamientoService; // Importa el servicio correcto
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException; // Import necesario si manejas la excepción aquí

import java.util.List;

@RestController
@RequestMapping("/api/tratamientos")
@RequiredArgsConstructor
public class TratamientoController { // Esta es la única clase que debe estar en este archivo

    private final TratamientoService tratamientoService; // Inyecta el servicio

    @GetMapping
    public ResponseEntity<List<TratamientoDTO>> obtenerTodos() {
        return ResponseEntity.ok(tratamientoService.obtenerTodosLosTratamientos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TratamientoDTO> obtenerPorId(@PathVariable Integer id) {
        return tratamientoService.obtenerTratamientoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TratamientoDTO> crearTratamiento(@RequestBody @Valid TratamientoCreateDTO dto) {
        // Asumiendo manejo global si especialidadId no existe
        try {
            TratamientoDTO nuevo = tratamientoService.guardarTratamiento(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) { // Captura la excepción de validación de FK del servicio
             // Considera un manejo de excepciones global (@ControllerAdvice) para devolver 400 o 404
             return ResponseEntity.badRequest().build(); // O .notFound().build() si es por ID no encontrado
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TratamientoDTO> actualizarTratamiento(@PathVariable Integer id, @RequestBody @Valid TratamientoCreateDTO dto) {
         try {
            TratamientoDTO actualizado = tratamientoService.actualizarTratamiento(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) { // Captura ID no encontrado o especialidadId inválida
             // Considera un manejo de excepciones global
             return ResponseEntity.notFound().build(); // Simplificado a 404
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTratamiento(@PathVariable Integer id) {
         try {
            tratamientoService.eliminarTratamiento(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) { // Captura ID no encontrado o error de integridad
             // Considera un manejo de excepciones global
            if (e.getCause() instanceof DataIntegrityViolationException) {
                 return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict si está en uso
            }
             return ResponseEntity.notFound().build(); // 404 si no se encontró
        }
    }

    // --- EL CÓDIGO DE TratamientoService YA NO ESTÁ AQUÍ ---

} // Fin de la clase TratamientoController