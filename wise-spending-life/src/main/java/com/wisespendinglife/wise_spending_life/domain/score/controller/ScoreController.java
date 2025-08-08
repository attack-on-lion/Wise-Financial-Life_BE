package com.wisespendinglife.wise_spending_life.domain.score.controller;

import com.wisespendinglife.wise_spending_life.domain.payment.service.PaymentServiceImpl;
import com.wisespendinglife.wise_spending_life.domain.score.dto.ScoreResponseDto;
import com.wisespendinglife.wise_spending_life.domain.score.service.ChatGptScoringClient;
import com.wisespendinglife.wise_spending_life.domain.score.service.ScoreServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/ai/score")
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreServiceImpl scoreService;
    private final PaymentServiceImpl paymentService;

    @PostMapping
    public ResponseEntity<ScoreResponseDto> calculate(@PathVariable Long userId) {
        ScoreResponseDto response = paymentService.calculateMonthlyScore(userId);
        saveScore(userId, response);
        return ResponseEntity.ok(response);
    }

    public boolean saveScore(Long userId, ScoreResponseDto score){
        return scoreService.saveScore(userId, score.getScore());
    }

}
