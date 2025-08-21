package com.wisespendinglife.wise_spending_life.domain.category.service;

import com.wisespendinglife.wise_spending_life.domain.category.dto.CategoryListResponseDto;
import com.wisespendinglife.wise_spending_life.domain.category.dto.CategoryRequestDto;
import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.category.entity.CategoryType;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NotificationType;

public interface CategoryService {
    void addCategory(CategoryRequestDto dto);
    CategoryListResponseDto findAll(CategoryType type);
    Category getEntity(Long categoryId);
    Category getEntity(String type);
}
