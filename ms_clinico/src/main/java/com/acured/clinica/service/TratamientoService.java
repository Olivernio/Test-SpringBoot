package com.acured.clinica.service;

import com.acured.clinica.entity.Tratamiento;
import com.acured.clinica.mapper.TratamientoMapper;
import com.acured.clinica.repository.EspecialidadRepository; // Para validar FK
import com.acured.clinica.repository.TratamientoRepository;
import com.acured.common.dto.TratamientoCreateDTO;
import com.acured.common.dto.TratamientoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException; // Para eliminar
import lombok.Getter; // <--- Make sure this is imported
import lombok.Setter; // <--- Make sure this is imported

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TratamientoService {

    private final TratamientoRepository tratamientoRepository;
    private final EspecialidadRepository especialidadRepository; // Para validar FK
    private final TratamientoMapper tratamientoMapper;

    @Transactional(readOnly = true)
    public List<TratamientoDTO> obtenerTodosLosTratamientos() {
        return tratamientoRepository.findAll().stream()
                .map(tratamientoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<TratamientoDTO> obtenerTratamientoPorId(Integer id) {
        return tratamientoRepository.findById(id)
                .map(tratamientoMapper::toDTO);
    }

    @Transactional
    public TratamientoDTO guardarTratamiento(TratamientoCreateDTO dto) {
        // **Validación FK**
        validarEspecialidad(dto.getEspecialidadId());

        Tratamiento tratamiento = tratamientoMapper.toEntity(dto);
        Tratamiento guardado = tratamientoRepository.save(tratamiento);
        return tratamientoMapper.toDTO(guardado);
    }

    @Transactional
    public TratamientoDTO actualizarTratamiento(Integer id, TratamientoCreateDTO dto) {
        Tratamiento existente = tratamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con id: " + id));

        // **Validación FK**
        validarEspecialidad(dto.getEspecialidadId());

        // Actualizar campos
        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setDuracionMin(dto.getDuracionMin());
        existente.setPrecio(dto.getPrecio());
        existente.setEspecialidadId(dto.getEspecialidadId());

        Tratamiento actualizado = tratamientoRepository.save(existente);
        return tratamientoMapper.toDTO(actualizado);
    }

    @Transactional
    public void eliminarTratamiento(Integer id) {
        if (!tratamientoRepository.existsById(id)) {
            throw new RuntimeException("Tratamiento no encontrado con id: " + id);
        }
        // El SQL tiene ON DELETE SET NULL para detalle_cita_tratamiento.tratamiento_id
        // El SQL tiene ON DELETE CASCADE para servicio_centro.tratamiento_id
        // Debería funcionar. Capturamos por si acaso.
        try {
            tratamientoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("No se puede eliminar el tratamiento, aún tiene referencias.", e);
        }
    }

    // --- Método auxiliar de validación ---
    private void validarEspecialidad(Integer especialidadId) {
        // Asumiendo que especialidadId es obligatorio según DTO
        if (especialidadId != null && !especialidadRepository.existsById(especialidadId)) {
            throw new RuntimeException("Especialidad no encontrada con ID: " + especialidadId);
        } else if (especialidadId == null) {
            throw new RuntimeException("El ID de la especialidad no puede ser nulo para un tratamiento.");
        }
    }
}
