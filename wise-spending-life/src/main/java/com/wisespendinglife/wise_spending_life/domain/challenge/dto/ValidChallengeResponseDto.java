package com.wisespendinglife.wise_spending_life.domain.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.Challenge;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
public class ValidChallengeResponseDto {
    private final Long id;
    private final String challengeName;
    private final ChallengeType challengeType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate startAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate endAt;

    private final long remainingDays; // DATEDIFF(endAt, CURRENT_DATE)

    public ValidChallengeResponseDto(Long id, String challengeName, ChallengeType challengeType,
                                     LocalDate startAt, LocalDate endAt, long remainingDays) {
        this.id = id;
        this.challengeName = challengeName;
        this.challengeType = challengeType;
        this.startAt = startAt;
        this.endAt = endAt;
        this.remainingDays = remainingDays;
    }

    public ValidChallengeResponseDto(Challenge challenge) {
        this.id = challenge.getId();
        this.challengeName = challenge.getChallengeName();
        this.challengeType = challenge.getChallengeType();
        this.startAt = challenge.getStartAt();
        this.endAt = challenge.getEndAt();
        this.remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), challenge.getEndAt());
    }
}
