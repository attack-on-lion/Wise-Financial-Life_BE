package com.wisespendinglife.wise_spending_life.domain.recommendation.controller;

import com.wisespendinglife.wise_spending_life.domain.recommendation.dto.RecommendationCreateRequestDto;
import com.wisespendinglife.wise_spending_life.domain.recommendation.dto.RecommendationCreateResponseDto;
import com.wisespendinglife.wise_spending_life.domain.recommendation.dto.RecommendationDetailResponseDto;
import com.wisespendinglife.wise_spending_life.domain.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping
    public ResponseEntity<?> recommend(
            @Validated @RequestBody RecommendationCreateRequestDto recommendationCreateRequestDto
    ){
        RecommendationCreateResponseDto res = recommendationService.generateRecommendation(recommendationCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{recommendation_id}")
    public ResponseEntity<?> getRecommendation(
            @PathVariable("recommendation_id") Long recommendationId
    ){
        RecommendationDetailResponseDto res = recommendationService.getById(recommendationId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/check")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(recommendationService.getAllRecommendations());
    }

    @GetMapping("/days/{challengeDays}")
    public ResponseEntity<?> getByChallengeDays(
            @PathVariable Long challengeDays
    ){
        return ResponseEntity.ok(recommendationService.getRecommendationsByDays(challengeDays));
    }
}
