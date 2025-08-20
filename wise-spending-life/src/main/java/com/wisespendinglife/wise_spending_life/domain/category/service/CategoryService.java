package com.wisespendinglife.wise_spending_life.domain.category.service;

import com.wisespendinglife.wise_spending_life.domain.category.entity.dto.CategoryListResponseDto;
import com.wisespendinglife.wise_spending_life.domain.category.entity.dto.CategoryRequestDto;

public interface CategoryService {
    void addCategory(CategoryRequestDto dto);
    CategoryListResponseDto findAll();
}
