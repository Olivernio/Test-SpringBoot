package com.acured.clinica.controller;

import com.acured.common.dto.TratamientoCreateDTO;
import com.acured.common.dto.TratamientoDTO;
import com.acured.clinica.service.TratamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinico/tratamientos")
public class TratamientoController {

    @Autowired
    private TratamientoService tratamientoService;

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
    public ResponseEntity<TratamientoDTO> crearTratamiento(@RequestBody TratamientoCreateDTO dto) {
        TratamientoDTO nuevo = tratamientoService.guardarTratamiento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TratamientoDTO> actualizarTratamiento(@PathVariable Integer id, @RequestBody TratamientoCreateDTO dto) {
        TratamientoDTO actualizado = tratamientoService.actualizarTratamiento(id, dto);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTratamiento(@PathVariable Integer id) {
        if (tratamientoService.obtenerTratamientoPorId(id).isPresent()) {
            tratamientoService.eliminarTratamiento(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}