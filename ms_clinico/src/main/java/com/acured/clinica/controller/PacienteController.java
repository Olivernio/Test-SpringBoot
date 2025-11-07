package com.acured.clinica.controller;

import com.acured.common.dto.PacienteCreateDTO;
import com.acured.common.dto.PacienteDTO;
import com.acured.clinica.service.PacienteService;
import jakarta.validation.Valid; // Necesario para @Valid
import lombok.RequiredArgsConstructor; // Necesario para inyección por constructor
// import org.springframework.beans.factory.annotation.Autowired; // Eliminado
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// import java.util.Optional; // Ya no se usa Optional aquí directamente

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor // <--- Reemplaza @Autowired
public class PacienteController {

    // @Autowired // Eliminado
    private final PacienteService pacienteService; // <--- Declarado como final

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> obtenerTodos() {
        List<PacienteDTO> pacientes = pacienteService.obtenerTodosLosPacientes();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> obtenerPorId(@PathVariable Integer id) {
        // El servicio maneja el Optional y devuelve DTO o lanza excepción/null
        return pacienteService.obtenerPacientePorId(id)
                .map(ResponseEntity::ok) // Si el Optional tiene valor, devuelve 200 OK
                .orElse(ResponseEntity.notFound().build()); // Si está vacío, devuelve 404 Not Found
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> crearPaciente(@RequestBody @Valid PacienteCreateDTO pacienteDTO) { // <--- Añadido @Valid
        PacienteDTO nuevoPaciente = pacienteService.guardarPaciente(pacienteDTO);
        // Si guardarPaciente lanza excepción por ID duplicado u otro error, se manejará globalmente
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> actualizarPaciente(@PathVariable Integer id, @RequestBody @Valid PacienteCreateDTO pacienteActualizadoDTO) { // <--- Añadido @Valid
        PacienteDTO actualizado = pacienteService.actualizarPaciente(id, pacienteActualizadoDTO);
        // El servicio devuelve DTO si actualiza, o lanza excepción/null si no encuentra el ID
        if (actualizado != null) { // Podrías cambiar esto si el servicio lanza excepción en lugar de null
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build(); // O manejar la excepción globalmente
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer id) {
        // El servicio ahora valida si existe y lanza excepción si no
        // Por lo tanto, no necesitamos el 'if' aquí si el servicio maneja la excepción
        try {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.noContent().build(); // 204 No Content si se elimina
        } catch (RuntimeException e) { // Captura la excepción si el servicio la lanza
            // Podrías tener un manejo de excepciones global (@ControllerAdvice)
            return ResponseEntity.notFound().build(); // 404 si no se encontró para eliminar
        }
        /* Código anterior:
        Optional<PacienteDTO> pacienteOpt = pacienteService.obtenerPacientePorId(id);
        if (pacienteOpt.isPresent()) {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
        */
    }
}