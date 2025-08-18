package com.wisespendinglife.wise_spending_life.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ItemRequestDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateItemDto {
        private String name;
        private String code;
        private String imageUrl;
        private Long price;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateItemDto {
        private Long id;
        private String name;
        private String code;
        private String imageUrl;
        private Long price;
    }
}
