package com.acured.clinica.mapper;

import com.acured.common.dto.CitaCreateDTO;
import com.acured.common.dto.CitaDTO;
import com.acured.clinica.entity.Cita;
import com.acured.clinica.entity.Paciente;
import com.acured.clinica.entity.CentroMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private CentroMedicoMapper centroMedicoMapper;

    public CitaDTO toDTO(Cita cita) {
        if (cita == null) return null;
        CitaDTO dto = new CitaDTO();
        dto.setId(cita.getId());
        dto.setTerapeutaId(cita.getTerapeutaId());
        dto.setFecha(cita.getFecha());
        dto.setEstado(cita.getEstado());
        dto.setMotivo(cita.getMotivo());

        // Mapea el paciente si no es nulo
        if (cita.getPaciente() != null) {
            dto.setPaciente(pacienteMapper.toDTO(cita.getPaciente()));
        }
        
        // Mapea el centro médico si no es nulo
        if (cita.getCentroMedico() != null) {
            dto.setCentroMedico(centroMedicoMapper.toDTO(cita.getCentroMedico()));
        }
        return dto;
    }

    public Cita toEntity(CitaCreateDTO dto) {
        if (dto == null) return null;
        Cita cita = new Cita();
        cita.setTerapeutaId(dto.getTerapeutaId());
        cita.setFecha(dto.getFecha());
        cita.setMotivo(dto.getMotivo());
        // El estado 'pendiente' se asigna por @PrePersist
        // Paciente y CentroMedico se asignarán en el Servicio
        return cita;
    }
}