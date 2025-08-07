package com.wisespendinglife.wise_spending_life.domain.score.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ScoreStates {

    /**
     * Category State
     *
     * @categoryName: 카테고리 이름
     * @totalAmount: 카테고리별 총 지출금액
     * @totalCount: 카테고리별 총 건수
     */
    @Getter
    @AllArgsConstructor
    @Builder
    public class CategoryState {
        private String categoryName;
        private Long totalAmount;
        private Long totalCount;
    }

    /**
     * Monthly Stats
     *
     * @totalIncome: 전월 총 수입
     * @totalOutflow: 전월 총 지출
     * @categoryStats: 카테고리별 합계 & 건수
     */
    @Getter
    @AllArgsConstructor
    @Builder
    public class MonthlyStats {
        private Long totalIncome;
        private Long totalOutflow;
        private List<CategoryState> categoryStats;
    }
}
