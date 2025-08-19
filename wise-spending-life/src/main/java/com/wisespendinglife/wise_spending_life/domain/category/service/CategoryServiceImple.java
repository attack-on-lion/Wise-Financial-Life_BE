package com.wisespendinglife.wise_spending_life.domain.category.service;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.category.converter.CategoryConverter;
import com.wisespendinglife.wise_spending_life.domain.category.dto.CategoryListResponseDto;
import com.wisespendinglife.wise_spending_life.domain.category.dto.CategoryRequestDto;
import com.wisespendinglife.wise_spending_life.domain.category.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImple implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Override
    public void addCategory(CategoryRequestDto dto) {
        Category category = categoryConverter.toEntity(dto);
        categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryListResponseDto findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryConverter.toDto(categories);
    }
}
