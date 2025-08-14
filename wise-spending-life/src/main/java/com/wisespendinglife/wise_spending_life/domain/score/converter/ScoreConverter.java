package com.wisespendinglife.wise_spending_life.domain.score.converter;

import com.wisespendinglife.wise_spending_life.domain.score.dto.ScoreResponseDto;
import com.wisespendinglife.wise_spending_life.domain.score.entity.Score;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
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
     * @param user
     * @param score
     * @return
     */
    public static Score toEntity(int score, User user) {
        return Score.builder()
                .user(user)
                .score(score)
                .build();
    }
}
