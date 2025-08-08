package com.wisespendinglife.wise_spending_life.domain.score.service;

import com.wisespendinglife.wise_spending_life.domain.score.dto.ScoreResponseDto;

public interface ScoreService {
    boolean saveScore(Long userId, Integer score);
    ScoreResponseDto getScore(Long userId);
}
