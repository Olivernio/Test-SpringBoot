package com.acured.clinica.mapper;

import com.acured.common.dto.CentroMedicoCreateDTO;
import com.acured.common.dto.CentroMedicoDTO;
import com.acured.clinica.entity.CentroMedico;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
// [WARNING] Can't initialize javac processor due to (most likely) a class loader problem: java.lang.NoClassDefFoundError: Could not initialize class lombok.javac.Javac ...
public interface CentroMedicoMapper {

    // WARNING POSIBLE: Corrección de los POM.XML
    CentroMedicoDTO toDTO(CentroMedico centro);
    
    CentroMedico toEntity(CentroMedicoCreateDTO dto);
}