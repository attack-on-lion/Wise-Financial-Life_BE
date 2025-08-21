package com.wisespendinglife.wise_spending_life.domain.category.service;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.category.converter.CategoryConverter;
import com.wisespendinglife.wise_spending_life.domain.category.dto.CategoryListResponseDto;
import com.wisespendinglife.wise_spending_life.domain.category.dto.CategoryRequestDto;
import com.wisespendinglife.wise_spending_life.domain.category.entity.CategoryType;
import com.wisespendinglife.wise_spending_life.domain.category.repository.CategoryRepository;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NotificationType;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImple implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Override
    public void addCategory(CategoryRequestDto dto) {
        if (categoryRepository.existsByNameIgnoreCaseAndType(dto.getName(), dto.getType()))
            throw new BusinessException(ErrorCode.DUPLICATE_CATEGORY_NAME);


        Category category = categoryConverter.toEntity(dto);
        categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryListResponseDto findAll(CategoryType type) {

        List<Category> categories;
        if(type == null){
             categories = categoryRepository.findAll();
        } else {
            categories = categoryRepository.findAllByType(type);
        }

        return categoryConverter.toListDto(categories);
    }

    @Override
    public Category getEntity(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

    }

    @Override
    public Category getEntity(String name) {
        return categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}
