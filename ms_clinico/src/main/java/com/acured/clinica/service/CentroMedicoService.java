package com.acured.clinica.service;

import com.acured.clinica.entity.CentroMedico;
import com.acured.clinica.mapper.CentroMedicoMapper;
import com.acured.clinica.repository.CentroMedicoRepository;
import com.acured.common.dto.CentroMedicoCreateDTO;
import com.acured.common.dto.CentroMedicoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CentroMedicoService {

    @Autowired
    private CentroMedicoRepository centroMedicoRepository;

    @Autowired
    private CentroMedicoMapper centroMedicoMapper;

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
        CentroMedico centro = centroMedicoMapper.toEntity(dto);
        CentroMedico guardado = centroMedicoRepository.save(centro);
        return centroMedicoMapper.toDTO(guardado);
    }

    @Transactional
    public CentroMedicoDTO actualizarCentro(Integer id, CentroMedicoCreateDTO dto) {
        Optional<CentroMedico> existenteOpt = centroMedicoRepository.findById(id);
        
        if (existenteOpt.isPresent()) {
            CentroMedico existente = existenteOpt.get();
            existente.setNombre(dto.getNombre());
            existente.setDireccion(dto.getDireccion());
            existente.setTelefono(dto.getTelefono());
            existente.setEmail(dto.getEmail());
            existente.setSitioWeb(dto.getSitioWeb());
            existente.setPaisId(dto.getPaisId());
            
            CentroMedico actualizado = centroMedicoRepository.save(existente);
            return centroMedicoMapper.toDTO(actualizado);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarCentro(Integer id) {
        centroMedicoRepository.deleteById(id);
    }
}