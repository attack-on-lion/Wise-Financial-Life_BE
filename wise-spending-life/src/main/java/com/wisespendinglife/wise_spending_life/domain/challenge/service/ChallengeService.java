package com.wisespendinglife.wise_spending_life.domain.challenge.service;

import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ChallengeCreateRequestDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ChallengeDetailResponseDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ValidChallengeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.Challenge;


public interface ChallengeService {
    public Challenge createChallenge(ChallengeCreateRequestDto dto);
    public ValidChallengeResponseDto findValidChallenge(boolean isCompleted, boolean isDeleted);
    public ChallengeDetailResponseDto findChallengeById(Long challenge_id);
    public void deleteChallenge(Long challenge_id);
}
