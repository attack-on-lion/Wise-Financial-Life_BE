package com.wisespendinglife.wise_spending_life.domain.recommendation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RecommendationItemDto {
    private final Long id;
    private final Long user_id;
    private final String challengeName;
    private final ChallengeType challengeType;
    private final Long challengeDays;
    private final LocalDateTime createdAt;
    private final List<String> categories;

    @Builder
    public RecommendationItemDto(Long id, Long user_id,String challengeName,
                                 ChallengeType challengeType, Long challengeDays,
                                 LocalDateTime createdAt, List<String> categories) {
        this.id = id;
        this.user_id = user_id;
        this.challengeName = challengeName;
        this.challengeType = challengeType;
        this.challengeDays = challengeDays;
        this.createdAt = createdAt;
        this.categories = categories;
    }
}
