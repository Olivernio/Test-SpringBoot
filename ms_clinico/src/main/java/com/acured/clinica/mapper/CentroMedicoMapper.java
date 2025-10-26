package com.acured.clinica.mapper;

import com.acured.common.dto.CentroMedicoDTO;
import com.acured.clinica.entity.CentroMedico;
<<<<<<< HEAD
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.BeanMapping;

@Mapper(componentModel = "spring")
public interface CentroMedicoMapper {

    CentroMedicoDTO toDTO(CentroMedico entity);

    CentroMedico toEntity(CentroMedicoDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CentroMedicoDTO dto, @MappingTarget CentroMedico entity);
=======

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
// [WARNING] Can't initialize javac processor due to (most likely) a class loader problem: java.lang.NoClassDefFoundError: Could not initialize class lombok.javac.Javac ...
public interface CentroMedicoMapper {

    // WARNING POSIBLE: Corrección de los POM.XML
    CentroMedicoDTO toDTO(CentroMedico centro);
    
    CentroMedico toEntity(CentroMedicoCreateDTO dto);
>>>>>>> 8f35b495d95e91b4973fd947cc2b1f23ff6af617
}