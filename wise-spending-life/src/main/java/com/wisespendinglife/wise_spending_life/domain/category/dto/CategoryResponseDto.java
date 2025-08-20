package com.wisespendinglife.wise_spending_life.domain.category.dto;

import com.wisespendinglife.wise_spending_life.domain.category.entity.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {
    private Long categoryId;
    private String categoryName;
    private CategoryType categoryType;
}
