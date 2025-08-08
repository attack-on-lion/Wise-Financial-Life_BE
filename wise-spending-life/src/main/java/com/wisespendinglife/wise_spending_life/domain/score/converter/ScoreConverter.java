package com.wisespendinglife.wise_spending_life.domain.score.converter;

import com.wisespendinglife.wise_spending_life.domain.score.dto.ScoreResponseDto;
import com.wisespendinglife.wise_spending_life.domain.score.entity.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScoreConverter {

    public static ScoreResponseDto toResponseDto(Score score) {
        return ScoreResponseDto.builder()
                .score(score.getScore())
                .build();
    }

    /**
     * TODO: userId 적용
     * @param userId
     * @param score
     * @return
     */
    public static Score toEntity(Long userId, int score) {
        return Score.builder()
                .score(score)
                .build();
    }
}
