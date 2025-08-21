package com.wisespendinglife.wise_spending_life.domain.recommendation.service;

import com.wisespendinglife.wise_spending_life.domain.recommendation.dto.RecommendationCreateRequestDto;
import com.wisespendinglife.wise_spending_life.domain.recommendation.dto.RecommendationCreateResponseDto;
import com.wisespendinglife.wise_spending_life.domain.recommendation.dto.RecommendationDetailResponseDto;
import com.wisespendinglife.wise_spending_life.domain.recommendation.dto.RecommendationSummaryDto;

import java.util.List;

public interface RecommendationService {
    public RecommendationCreateResponseDto generateRecommendation(RecommendationCreateRequestDto recommendationCreateRequestDto);
    public RecommendationDetailResponseDto getById(Long recommendationId);
    List<RecommendationSummaryDto> getAllRecommendations();
    List<RecommendationSummaryDto> getRecommendationsByDays(Long challengeDays);
}
