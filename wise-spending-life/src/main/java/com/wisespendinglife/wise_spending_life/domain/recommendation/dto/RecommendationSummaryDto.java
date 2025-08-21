package com.wisespendinglife.wise_spending_life.domain.recommendation.dto;

import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RecommendationSummaryDto {
    private final String challengeName;
    private final ChallengeType challengeType;
    private final Long challengeDays;
}
