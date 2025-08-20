package com.wisespendinglife.wise_spending_life.domain.character.dto;

import lombok.*;

import java.util.List;

public class CharacterResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CharacterCreateDto{
        private Long id;
    }

    /**
     * 요약
     */
    @Getter @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Summary {
        private long count;  // 전체 개수
    }

    /**
     * 페이지네이션
     */
    @Getter
    @Builder @AllArgsConstructor @NoArgsConstructor
    public static class PageInfo {
        private int page;  // default = 0
        private int size;  // default = 15
        private boolean hasNext;
    }

    /**
     * 캐릭터 레코드
     */
    @Getter @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Item {
        private Long id;
        private String code;
        private String name;
        private Long price;
        private String imageUrl;
    }

    /**
     * 캐릭터 목록 반환 ResponseDto
     */
    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class Characters {
        private Summary summary;
        private PageInfo pageInfo;
        private List<Item> items;
    }

}
