package com.wisespendinglife.wise_spending_life.domain.recommendation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class RecommendationDetailResponseDto {
    private final Long id;
    private final String challengeName;
    private final ChallengeType challengeType;
    private final Long challengeDays;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate createdAt;
}
