package com.edutech.courses.service;

import com.edutech.common.dto.CourseDTO;
import com.edutech.courses.client.UserClient;
import com.edutech.courses.entity.Course;
import com.edutech.courses.mapper.CourseMapper;
import com.edutech.courses.repository.CourseCategoryRepository;
import com.edutech.courses.repository.CourseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.edutech.common.exception.ExceptionUtils.orThrow;
import static com.edutech.common.exception.ExceptionUtils.orThrowFeign;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepo;
    private final CourseCategoryRepository categRepo;
    private final CourseMapper courseMapper;
    private final UserClient userClient;

    @Transactional(readOnly = true)
    public List<CourseDTO> findAll() {
        return courseRepo.findAll().stream()
                .map(courseMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseDTO findById(Integer id) {
        return courseMapper.toDTO(orThrow(courseRepo.findById(id), "Curso"));
    }

    /*
     * CREAR UN NUEVO CURSO:
     * AHORA SE USARÁ LA COMUNICACIÓN CON EL MICROSERVICIO DE IDENTIDAD PARA VALIDAR
     * SI EL COORDINADOR E INSTRUCTOR DEL CURSO QUE SE VA A CREAR, REALMENTE
     * EXISTEN EN LA BASE DE DATOS, Y EN CASO CONTRARIO ARROJAR LOS MENSAJES
     * DE ERROR ADECUADOS.
     */

    @Transactional
    public CourseDTO create(CourseDTO dto) {
        // Verificar que la categoría exista
        orThrow(categRepo.findById(dto.getCategoryId()), "Categoría");

        // Validar que el coordinador exista en el microservicio de identidad
        orThrowFeign(dto.getManagerId(), userClient::findById, "Coordinador");

        // Validar que el instructor exista en el microservicio de identidad
        orThrowFeign(dto.getInstructorId(), userClient::findById, "Instructor");

        return saveDTO(dto, null);
    }

    @Transactional
    public CourseDTO update(Integer id, CourseDTO dto) {
        orThrow(courseRepo.findById(id), "Curso");
        return saveDTO(dto, id);
    }

    @Transactional
    public void delete(Integer id) {
        courseRepo.delete(orThrow(courseRepo.findById(id), "Curso"));
    }

    private CourseDTO saveDTO(CourseDTO dto, Integer id) {
        Course entity = courseMapper.toEntity(dto);
        if (id != null) {
            entity.setId(id);
        }
        return courseMapper.toDTO(courseRepo.save(entity));
    }
}