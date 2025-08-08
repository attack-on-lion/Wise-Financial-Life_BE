package com.wisespendinglife.wise_spending_life.domain.score.service;

import com.wisespendinglife.wise_spending_life.domain.score.converter.ScoreConverter;
import com.wisespendinglife.wise_spending_life.domain.score.dto.ScoreResponseDto;
import com.wisespendinglife.wise_spending_life.domain.score.entity.Score;
import com.wisespendinglife.wise_spending_life.domain.score.repository.ScoreRepository;
import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
import com.wisespendinglife.wise_spending_life.domain.user.repository.UserRepository;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;
    private final ScoreConverter scoreConverter;
    private final UserRepository userRepository;

    public boolean saveScore(Long userId, Integer score){

        UserEntity user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Score entity = scoreConverter.toEntity(score, user);
        scoreRepository.save(entity);

        return true;
    }

    /**
     * 현재 최근 점수 조회.
     *
     * TODO: userId 적용
     * @param userId
     * @return
     */
    public ScoreResponseDto getScore(Long userId){
        Optional<Score> last = scoreRepository.findFirstByUser_IdOrderByCreatedAtDesc(userId);
        if(last.isEmpty()) throw new BusinessException(ErrorCode.SCORE_NOT_FOUND);
        log.info("Score found: {}", last.get());

        return scoreConverter.toResponseDto(last.get());

    }
}
