package com.wisespendinglife.wise_spending_life.domain.score.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ScoreResponseDto {
    private Integer score;
}
