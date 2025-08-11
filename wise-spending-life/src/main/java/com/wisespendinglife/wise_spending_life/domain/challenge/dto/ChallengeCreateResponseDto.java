package com.wisespendinglife.wise_spending_life.domain.challenge.dto;

import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ChallengeCreateResponseDto {
    private final Long id;
    private final Long userId;
    private final String challengeName;
    private final ChallengeType challengeType;
    private final Long challengeDays;
    private final LocalDate startAt;
    private final LocalDate endAt;
    private final LocalDate createdAt;
    private final List<String> categories;
    private final String characterImageUrl;
    private final Boolean isCompleted;
    private final Boolean isDeleted;

    @Builder
    public ChallengeCreateResponseDto(Long id, Long userId, String challengeName,
                                      ChallengeType challengeType, Long challengeDays, LocalDate startAt,
                                      LocalDate endAt, LocalDate createdAt, List<String> categories,
                                      String characterImageUrl, Boolean isCompleted, Boolean isDeleted) {
        this.id = id;
        this.userId = userId;
        this.challengeName = challengeName;
        this.challengeType = challengeType;
        this.challengeDays = challengeDays;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createdAt = createdAt;
        this.categories = categories;
        this.characterImageUrl = characterImageUrl;
        this.isCompleted = isCompleted;
        this.isDeleted = isDeleted;
    }
}
