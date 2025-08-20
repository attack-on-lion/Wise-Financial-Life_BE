package com.wisespendinglife.wise_spending_life.domain.category.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListResponseDto {
    List<String> categories;
}
