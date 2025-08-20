package com.wisespendinglife.wise_spending_life.domain.composite.dto;

import lombok.*;

public class CompositeRequestDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreatComposite {
        private String itemName;
        private String characterName;
        private String imageUrl;
    }
}
