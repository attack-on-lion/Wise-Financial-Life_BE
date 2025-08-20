package com.wisespendinglife.wise_spending_life.domain.category.converter;

import com.wisespendinglife.wise_spending_life.domain.category.dto.CategoryResponseDto;
import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.category.dto.CategoryListResponseDto;
import com.wisespendinglife.wise_spending_life.domain.category.dto.CategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

    public CategoryResponseDto toDto(Category category){

        return CategoryResponseDto.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .categoryType(category.getType())
                .build();
    }

    public List<CategoryResponseDto> toDto(List<Category> categories) {
        return categories.stream()
                .map(this::toDto)
                .toList();
    }

    public CategoryListResponseDto toListDto(List<Category> categories){
        return CategoryListResponseDto.builder()
                .categories(toDto(categories))
                .build();
    }



}
