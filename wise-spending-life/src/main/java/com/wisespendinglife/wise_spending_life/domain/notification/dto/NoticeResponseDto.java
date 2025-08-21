package com.wisespendinglife.wise_spending_life.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class NoticeResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NoticCreateResponseDto {
        private Long id;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Notices {
        private Summary summary;
        private PageInfo pageInfo;
        private Items items;
    }


    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Summary {
        private int count;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageInfo {
        private int page;
        private int size;
        private boolean hasNext;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Items {
        List<Notice> notices;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Notice {
        private Long id;
        private Long userId;
        private String content;
        private String categoryName;
        private LocalDateTime createdAt;
    }

}
