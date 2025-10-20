package com.acured.clinica.controller;

// import com.acured.clinica.entity.Paciente; // <-- Ya no usamos la entidad aquí
import com.acured.common.dto.PacienteCreateDTO; // IMPORTANTE
import com.acured.common.dto.PacienteDTO; // IMPORTANTE
import com.acured.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clinico/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> obtenerTodos() { // <-- Devuelve DTO
        List<PacienteDTO> pacientes = pacienteService.obtenerTodosLosPacientes();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> obtenerPorId(@PathVariable Integer id) { // <-- Devuelve DTO
        Optional<PacienteDTO> pacienteOpt = pacienteService.obtenerPacientePorId(id);
        
        return pacienteOpt
                .map(paciente -> ResponseEntity.ok(paciente))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> crearPaciente(@RequestBody PacienteCreateDTO pacienteDTO) { // <-- Recibe DTO
        PacienteDTO nuevoPaciente = pacienteService.guardarPaciente(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> actualizarPaciente(@PathVariable Integer id, @RequestBody PacienteCreateDTO pacienteActualizadoDTO) { // <-- Recibe DTO
        PacienteDTO actualizado = pacienteService.actualizarPaciente(id, pacienteActualizadoDTO);
        
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer id) {
        // Para el delete, el servicio puede seguir igual
        Optional<PacienteDTO> pacienteOpt = pacienteService.obtenerPacientePorId(id);
        if (pacienteOpt.isPresent()) {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}