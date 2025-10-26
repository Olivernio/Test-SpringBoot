package com.acured.clinica.service;

import com.acured.clinica.entity.CentroMedico;
import com.acured.clinica.mapper.CentroMedicoMapper;
import com.acured.clinica.repository.CentroMedicoRepository;
// Importar Repositorio o Cliente si validas paisId
// import com.acured.clinica.repository.PaisRepository; // Si tuvieras tabla local
// import com.acured.clinica.client.PaisClient; // Si fuera externo
import com.acured.common.dto.CentroMedicoCreateDTO;
import com.acured.common.dto.CentroMedicoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException; // Para manejar FKs al eliminar
import lombok.Getter; // <--- Make sure this is imported
import lombok.Setter; // <--- Make sure this is imported

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CentroMedicoService {

    private final CentroMedicoRepository centroMedicoRepository;
    private final CentroMedicoMapper centroMedicoMapper;
    // private final PaisRepository paisRepository; // O PaisClient

    @Transactional(readOnly = true)
    public List<CentroMedicoDTO> obtenerTodosLosCentros() {
        return centroMedicoRepository.findAll().stream()
                .map(centroMedicoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<CentroMedicoDTO> obtenerCentroPorId(Integer id) {
        return centroMedicoRepository.findById(id)
                .map(centroMedicoMapper::toDTO);
    }

    @Transactional
    public CentroMedicoDTO guardarCentro(CentroMedicoCreateDTO dto) {
        // **Validación FK (Externa/Local): paisId**
        validarPais(dto.getPaisId()); // Llama al método de validación

        CentroMedico centro = centroMedicoMapper.toEntity(dto);
        CentroMedico guardado = centroMedicoRepository.save(centro);
        return centroMedicoMapper.toDTO(guardado);
    }

    @Transactional
    public CentroMedicoDTO actualizarCentro(Integer id, CentroMedicoCreateDTO dto) {
        CentroMedico existente = centroMedicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Centro médico no encontrado con id: " + id));

        // **Validación FK (Externa/Local): paisId**
        validarPais(dto.getPaisId()); // Llama al método de validación

        // Actualizar campos
        existente.setNombre(dto.getNombre());
        existente.setDireccion(dto.getDireccion());
        existente.setTelefono(dto.getTelefono());
        existente.setEmail(dto.getEmail());
        existente.setSitioWeb(dto.getSitioWeb());
        existente.setPaisId(dto.getPaisId());

        CentroMedico actualizado = centroMedicoRepository.save(existente);
        return centroMedicoMapper.toDTO(actualizado);
    }

    @Transactional
    public void eliminarCentro(Integer id) {
        if (!centroMedicoRepository.existsById(id)) {
            throw new RuntimeException("Centro médico no encontrado con id: " + id);
        }
        // El SQL tiene ON DELETE SET NULL para citas.centro_id
        // El SQL tiene ON DELETE CASCADE para servicio_centro.centro_id
        // Debería funcionar, pero capturamos por si acaso.
        try {
            centroMedicoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // Esto no debería pasar con SET NULL/CASCADE, pero es buena práctica capturarlo
             throw new RuntimeException("No se puede eliminar el centro médico, aún tiene referencias.", e);
        }
    }

    // --- Método auxiliar para validar Pais ---
    private void validarPais(Integer paisId) {
        if (paisId != null) {
            // Aquí iría la lógica para validar si el paisId existe
            // usando un Repositorio local o un Feign Client.
            // Ejemplo (si fuera local):
            // if (!paisRepository.existsById(paisId)) {
            //     throw new RuntimeException("País no encontrado con ID: " + paisId);
            // }
            // Ejemplo (si fuera externo):
            // try { paisClient.findById(paisId); } catch (FeignException.NotFound e) { ... }
            System.out.println("ADVERTENCIA: Validación de paisId no implementada."); // Placeholder
        }
    }
}