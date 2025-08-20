package com.wisespendinglife.wise_spending_life.domain.composite.dto;

import lombok.*;

import java.util.List;

public class CompositeResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResponse {
        private Long id;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CompositeResponse {
        private Long id;
        private Long characterId;
        private Long itemId;
        private String imageUrl;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Composites {
        private Summary summary;
        private PageInfo pageInfo;
        private List<CompositeResponse> items;
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
}
