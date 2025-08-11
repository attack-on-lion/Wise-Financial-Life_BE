package com.wisespendinglife.wise_spending_life.domain.challenge.service;

import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ChallengeCreateRequestDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ValidChallengeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.Challenge;


public interface ChallengeService {
    public Challenge createChallenge(ChallengeCreateRequestDto dto);
    public ValidChallengeResponseDto findValidChallenge(boolean isCompleted, boolean isDeleted);
}
