package com.acured.clinica.service;

import com.acured.clinica.entity.Paciente;
import com.acured.clinica.mapper.PacienteMapper;
import com.acured.clinica.repository.PacienteRepository;
import com.acured.common.dto.PacienteCreateDTO;
import com.acured.common.dto.PacienteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.Getter; // <--- Make sure this is imported
import lombok.Setter; // <--- Make sure this is imported
// Importar Feign Client si validas usuarioId
// import com.acured.clinica.client.UserClient; // Asumiendo que exista

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;
    // private final UserClient userClient; // Si validas usuarioId

    @Transactional(readOnly = true)
    public List<PacienteDTO> obtenerTodosLosPacientes() {
        return pacienteRepository.findAll()
                .stream()
                .map(pacienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<PacienteDTO> obtenerPacientePorId(Integer id) {
        return pacienteRepository.findById(id)
                .map(pacienteMapper::toDTO);
    }

    @Transactional
    public PacienteDTO guardarPaciente(PacienteCreateDTO pacienteDTO) {
        // **Validación FK (Externa): usuarioId**
        // Se debería validar que el usuarioId existe, probablemente llamando
        // a otro microservicio (ej: ms_usuarios) a través de un Feign Client.
        // Ejemplo: validarUsuario(pacienteDTO.getUsuarioId());
        // if (!userClient.usuarioExiste(pacienteDTO.getUsuarioId())) {
        //     throw new RuntimeException("Usuario no encontrado con ID: " + pacienteDTO.getUsuarioId());
        // }

        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        return pacienteMapper.toDTO(pacienteGuardado);
    }

    @Transactional
    public void eliminarPaciente(Integer id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente no encontrado con id: " + id); // O excepción personalizada
        }
        // Considerar el ON DELETE CASCADE del SQL: al borrar paciente, se borrarán citas e historiales asociados.
        pacienteRepository.deleteById(id);
    }

    @Transactional
    public PacienteDTO actualizarPaciente(Integer id, PacienteCreateDTO pacienteActualizadoDTO) {
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));

        // **Validación FK (Externa): usuarioId** (igual que en guardar)
        // Ejemplo: validarUsuario(pacienteActualizadoDTO.getUsuarioId());

        // Actualizar campos (se puede usar MapStruct con @MappingTarget para esto)
        pacienteExistente.setUsuarioId(pacienteActualizadoDTO.getUsuarioId());
        pacienteExistente.setFechaNacimiento(pacienteActualizadoDTO.getFechaNacimiento());
        pacienteExistente.setGenero(pacienteActualizadoDTO.getGenero());
        pacienteExistente.setObservaciones(pacienteActualizadoDTO.getObservaciones());

        Paciente pacienteActualizado = pacienteRepository.save(pacienteExistente);
        return pacienteMapper.toDTO(pacienteActualizado);
    }

    // --- Ejemplo método validación externa ---
    // private void validarUsuario(Integer usuarioId) {
    //    if (usuarioId != null) { // Asumiendo que puede ser nulo si la lógica lo permite
    //        try {
    //             // Suponiendo que tienes un UserClient con un método que devuelve UserDTO o lanza excepción 404
    //             userClient.findById(usuarioId);
    //        } catch (FeignException.NotFound e) {
    //             throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId, e);
    //        } catch (Exception e) {
    //             // Manejar otros errores de comunicación
    //             throw new RuntimeException("Error al validar usuario con ID: " + usuarioId, e);
    //        }
    //    }
    // }
}