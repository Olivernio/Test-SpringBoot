package com.acured.clinica.mapper;

import com.acured.common.dto.CentroMedicoCreateDTO;
import com.acured.common.dto.CentroMedicoDTO;
import com.acured.clinica.entity.CentroMedico;
import org.springframework.stereotype.Component;

@Component
public class CentroMedicoMapper {

    public CentroMedicoDTO toDTO(CentroMedico centro) {
        if (centro == null) return null;
        CentroMedicoDTO dto = new CentroMedicoDTO();
        dto.setId(centro.getId());
        dto.setNombre(centro.getNombre());
        dto.setDireccion(centro.getDireccion());
        dto.setTelefono(centro.getTelefono());
        dto.setEmail(centro.getEmail());
        dto.setSitioWeb(centro.getSitioWeb());
        dto.setPaisId(centro.getPaisId());
        return dto;
    }

    public CentroMedico toEntity(CentroMedicoCreateDTO dto) {
        if (dto == null) return null;
        CentroMedico centro = new CentroMedico();
        centro.setNombre(dto.getNombre());
        centro.setDireccion(dto.getDireccion());
        centro.setTelefono(dto.getTelefono());
        centro.setEmail(dto.getEmail());
        centro.setSitioWeb(dto.getSitioWeb());
        centro.setPaisId(dto.getPaisId());
        return centro;
    }
}