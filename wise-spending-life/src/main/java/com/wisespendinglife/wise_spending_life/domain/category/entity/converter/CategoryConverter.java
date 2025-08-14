package com.wisespendinglife.wise_spending_life.domain.category.entity.converter;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.category.entity.dto.CategoryListResponseDto;
import com.wisespendinglife.wise_spending_life.domain.category.entity.dto.CategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryConverter {

    public Category toEntity(CategoryRequestDto dto){
        return Category.builder()
                .name(dto.getName())
                .type(dto.getType())
                .build();
    }

    public CategoryListResponseDto toDto(List<Category> categories) {
        List<String> categoriesNames = categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());

        return CategoryListResponseDto.builder()
                .categories(categoriesNames)
                .build();
    }



}
