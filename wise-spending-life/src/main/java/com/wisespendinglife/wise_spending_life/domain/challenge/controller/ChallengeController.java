package com.wisespendinglife.wise_spending_life.domain.challenge.controller;

import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ChallengeCreateRequestDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ChallengeDetailResponseDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ValidChallengeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.Challenge;
import com.wisespendinglife.wise_spending_life.domain.challenge.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
public class ChallengeController {
    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<?> createChallenge(
            @Validated @RequestBody ChallengeCreateRequestDto challengeCreateRequestDto
    ){
        Challenge createdChallenge = challengeService.createChallenge(challengeCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("msg", "챌린지가 성공적으로 생성되었습니다."));
    }

    @GetMapping
    public ResponseEntity<ValidChallengeResponseDto> getValidChallenge(
            @RequestParam("isCompleted") boolean isCompleted,
            @RequestParam("isDeleted") boolean isDeleted
    ){
        ValidChallengeResponseDto validChallenge = challengeService.findValidChallenge(isCompleted, isDeleted);
        return ResponseEntity.ok(validChallenge);
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<ChallengeDetailResponseDto> getChallengeById(
            @PathVariable("challengeId") Long challengeId
    ){
        ChallengeDetailResponseDto challengeDetailResponseDto = challengeService.findChallengeById(challengeId);
        return ResponseEntity.ok(challengeDetailResponseDto);
    }

    @DeleteMapping("/{challengeId}")
    public ResponseEntity<?> deleteChallenge(
            @PathVariable("challengeId") Long challengeId
    ){
        challengeService.deleteChallenge(challengeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("msg", "챌린지를 성공적으로 삭제했습니다."));
    }
}
