package com.acured.clinica.mapper;

import com.acured.common.dto.PacienteCreateDTO;
import com.acured.common.dto.PacienteDTO;
import com.acured.clinica.entity.Paciente;
// import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;
import java.util.List;

// MAPPER TEMPORALMENTE DESACTIVADO - MapStruct causaba problemas
// @Mapper(componentModel = "spring")
public interface PacienteMapper {

    // TODOS LOS MÉTODOS COMENTADOS TEMPORALMENTE
    /*
    @Mapping(source = "usuarioId", target = "usuarioId")
    PacienteDTO toDTO(Paciente paciente);

    @Mapping(source = "usuarioId", target = "usuarioId")
    List<PacienteDTO> toDTOList(List<Paciente> pacientes);

    Paciente toEntity(PacienteCreateDTO dto);
    */
}
