package com.edutech.courses.service;

import com.edutech.common.dto.CourseCategoryDTO;
import com.edutech.courses.entity.CourseCategory;
import com.edutech.courses.mapper.CourseCategoryMapper;
import com.edutech.courses.repository.CourseCategoryRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.edutech.common.exception.ExceptionUtils.orThrow;

@Service
@RequiredArgsConstructor
public class CourseCategoryService {

    private final CourseCategoryRepository categRepo;
    private final CourseCategoryMapper categMapper;

    @Transactional(readOnly = true)
    public List<CourseCategoryDTO> findAll() {
        return categRepo.findAll().stream().map(categMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public CourseCategoryDTO findById(Integer id) {
        return categMapper.toDTO(orThrow(categRepo.findById(id), "Categoría"));
    }

    @Transactional
    public CourseCategoryDTO create(CourseCategoryDTO dto) {
        return saveDTO(dto, null);
    }

    @Transactional
    public CourseCategoryDTO update(Integer id, CourseCategoryDTO dto) {
        orThrow(categRepo.findById(id), "Categoría");
        return saveDTO(dto, id);
    }

    @Transactional
    public void delete(Integer id) {
        categRepo.delete(orThrow(categRepo.findById(id), "Categoría"));
    }

    private CourseCategoryDTO saveDTO(CourseCategoryDTO dto, Integer id) {
        CourseCategory entity = categMapper.toEntity(dto);
        if (id != null) {
            entity.setId(id);
        }
        return categMapper.toDTO(categRepo.save(entity));
    }
}
