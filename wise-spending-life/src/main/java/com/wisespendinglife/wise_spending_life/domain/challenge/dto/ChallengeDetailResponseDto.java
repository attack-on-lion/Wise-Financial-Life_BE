package com.wisespendinglife.wise_spending_life.domain.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.Challenge;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeType;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ChallengeDetailResponseDto {
    private final Long id;
    private final String challengeName;
    private final ChallengeType challengeType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate startAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate endAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate createdAt;

    private final List<String> categories;

    public ChallengeDetailResponseDto(Challenge challenge) {
        this.id = challenge.getId();
        this.challengeName = challenge.getChallengeName();
        this.challengeType = challenge.getChallengeType();
        this.startAt = challenge.getStartAt();
        this.endAt = challenge.getEndAt();
        this.createdAt = challenge.getCreatedAt();
        this.categories = challenge.getChallengeCategories().stream()
                .map(challengeCategory -> challengeCategory.getCategory().getName())
                .collect(Collectors.toList());
    }
}