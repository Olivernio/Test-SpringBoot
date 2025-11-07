package com.acured.profesionales.mapper;

import com.acured.common.dto.CentroMedicoCreateDTO;
import com.acured.common.dto.CentroMedicoDTO;
import com.acured.profesionales.entity.CentroMedico;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-07T15:31:58-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class CentroMedicoMapperImpl implements CentroMedicoMapper {

    @Override
    public CentroMedicoDTO toDTO(CentroMedico centro) {
        if ( centro == null ) {
            return null;
        }

        CentroMedicoDTO centroMedicoDTO = new CentroMedicoDTO();

        centroMedicoDTO.setId( centro.getId() );
        centroMedicoDTO.setNombre( centro.getNombre() );
        centroMedicoDTO.setDireccion( centro.getDireccion() );
        centroMedicoDTO.setTelefono( centro.getTelefono() );
        centroMedicoDTO.setEmail( centro.getEmail() );
        centroMedicoDTO.setSitioWeb( centro.getSitioWeb() );
        centroMedicoDTO.setPaisId( centro.getPaisId() );

        return centroMedicoDTO;
    }

    @Override
    public CentroMedico toEntity(CentroMedicoCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CentroMedico centroMedico = new CentroMedico();

        centroMedico.setNombre( dto.getNombre() );
        centroMedico.setDireccion( dto.getDireccion() );
        centroMedico.setTelefono( dto.getTelefono() );
        centroMedico.setEmail( dto.getEmail() );
        centroMedico.setPaisId( dto.getPaisId() );
        centroMedico.setSitioWeb( dto.getSitioWeb() );

        return centroMedico;
    }

    @Override
    public void updateEntityFromDto(CentroMedicoCreateDTO dto, CentroMedico entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getNombre() != null ) {
            entity.setNombre( dto.getNombre() );
        }
        if ( dto.getDireccion() != null ) {
            entity.setDireccion( dto.getDireccion() );
        }
        if ( dto.getTelefono() != null ) {
            entity.setTelefono( dto.getTelefono() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getPaisId() != null ) {
            entity.setPaisId( dto.getPaisId() );
        }
        if ( dto.getSitioWeb() != null ) {
            entity.setSitioWeb( dto.getSitioWeb() );
        }
    }
}
