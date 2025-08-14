package com.wisespendinglife.wise_spending_life.domain.point.dto;

import com.wisespendinglife.wise_spending_life.domain.point.entity.SourceKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PointResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PointBalanceResponseDto {
        private Long balance;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PointListResponseDto {
        private Summary summary;
        private PageInfo pageInfo;
        private List<Item> items;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Summary {
        private long count; // 전체 포인트 내역 건수
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class PageInfo {
        private int page;  // 현재 페이지 번호
        private int size;  // 요청한 페이지 사이즈
        private boolean hasNext; // 다음 페이지 존재 여부
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Item {
        private Long id;
        private Long userId;
        private SourceKind sourceKind; // 포인트 발생/차감 종류
        private Long delta;
        private Long balance;
        private Long challengeDays;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
