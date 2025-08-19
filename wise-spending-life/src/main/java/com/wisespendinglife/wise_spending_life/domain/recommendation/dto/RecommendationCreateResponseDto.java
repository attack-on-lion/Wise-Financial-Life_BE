package com.wisespendinglife.wise_spending_life.domain.recommendation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class RecommendationCreateResponseDto {
    private final Long user_id;
    private final List<RecommendationItemDto> recommendations;

    @Builder
    public RecommendationCreateResponseDto(Long user_id, List<RecommendationItemDto> recommendations) {
        this.user_id = user_id;
        this.recommendations = recommendations;
    }
}
