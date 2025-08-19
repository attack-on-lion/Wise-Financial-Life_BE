package com.wisespendinglife.wise_spending_life.domain.category.dto;

import com.wisespendinglife.wise_spending_life.domain.category.entity.CategoryType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {
    @NotNull(message = "{INVALID_NOT_NULL}")
    @Max(value = 10)
    private String name;
    private CategoryType type;
}
