package com.wisespendinglife.wise_spending_life.domain.score.service;

import com.wisespendinglife.wise_spending_life.domain.payment.service.PaymentService;
import com.wisespendinglife.wise_spending_life.domain.score.converter.ScoreConverter;
import com.wisespendinglife.wise_spending_life.domain.score.dto.ScoreResponseDto;
import com.wisespendinglife.wise_spending_life.domain.score.entity.Score;
import com.wisespendinglife.wise_spending_life.domain.score.repository.ScoreRepository;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import com.wisespendinglife.wise_spending_life.domain.user.repository.UserRepository;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;
    private final ScoreConverter scoreConverter;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    public boolean saveScore(Long userId, Integer score){

        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Score entity = scoreConverter.toEntity(score, user);
        scoreRepository.save(entity);

        return true;
    }

    public ScoreResponseDto getMonthlyScore(Long userId) {
        YearMonth ym = YearMonth.now(ZoneId.of("Asia/Seoul"));

        LocalDateTime start = ym.atDay(1).atStartOfDay();
        LocalDateTime end = ym.atEndOfMonth().atTime(LocalTime.MAX);

        // 1) 이번 달 스코어 있으면 반환
        Optional<Score> existing = scoreRepository.findMonthlyByUserId(userId, start, end);
        if (existing.isPresent()) {
            return scoreConverter.toResponseDto(existing.get());
        }

        // 2) 없으면 계산 → 저장 → 반환
        ScoreResponseDto calculated = paymentService.calculateMonthlyScore(userId);
        return calculated;
    }
}
