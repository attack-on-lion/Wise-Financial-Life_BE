package com.wisespendinglife.wise_spending_life.domain.character.dto;

import lombok.*;

public class CharacterRequestDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class CreateCharacterDto {
        private String name;
        private String code;
        private String imageUrl;
        private Long price;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class UpdateCharacterDto {
        private Long id;
        private String name;
        private String code;
        private String imageUrl;
        private Long price;
    }
}
