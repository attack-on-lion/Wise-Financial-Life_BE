package com.wisespendinglife.wise_spending_life.domain.notification.dto;

import com.wisespendinglife.wise_spending_life.domain.category.entity.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NoticeRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateNoticDto {
        private Long userId;
        private CategoryType categoryName;
        private String content;
    }

}
