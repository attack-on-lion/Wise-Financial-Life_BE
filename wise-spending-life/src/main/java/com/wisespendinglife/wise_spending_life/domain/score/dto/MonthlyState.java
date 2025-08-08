package com.wisespendinglife.wise_spending_life.domain.score.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
public class MonthlyState {
    private Long totalIncome;
    private Long totalOutflow;
    private List<CategoryState> categoryStates;
}
