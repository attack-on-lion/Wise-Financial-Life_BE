package com.wisespendinglife.wise_spending_life.domain.challenge.controller;

import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ChallengeCreateRequestDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ChallengeCreateResponseDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ChallengeDetailResponseDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ValidChallengeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.Challenge;
import com.wisespendinglife.wise_spending_life.domain.challenge.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
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
        ChallengeCreateResponseDto responseDto = ChallengeCreateResponseDto.builder()
                .id(createdChallenge.getId())
                .user_id(createdChallenge.getUser().getId())
                .challengeName(createdChallenge.getChallengeName())
                .challengeType(createdChallenge.getChallengeType())
                .challengeDays(createdChallenge.getChallengeDays())
                .startAt(createdChallenge.getStartAt())
                .endAt(createdChallenge.getEndAt())
                .createdAt(createdChallenge.getCreatedAt())
                .categories(createdChallenge.getChallengeCategories()
                        .stream()
                        .map(v -> v.getCategory().getName())
                        .toList())
                .characterImageUrl(createdChallenge.getCharacterImageUrl())
                .isCompleted(createdChallenge.getIsCompleted())
                .isDeleted(createdChallenge.getIsDeleted())
                .build();

        log.info(">>> [CTRL] Created challenge -> {}", responseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<ValidChallengeResponseDto> getValidChallenge(
            @RequestParam("isCompleted") boolean isCompleted,
            @RequestParam("isDeleted") boolean isDeleted
    ){
        ValidChallengeResponseDto validChallenge = challengeService.findValidChallenge(isCompleted, isDeleted);
        return ResponseEntity.ok(validChallenge);
    }

    @GetMapping("/{challenge_id}")
    public ResponseEntity<ChallengeDetailResponseDto> getChallengeById(
            @PathVariable("challenge_id") Long challenge_id
    ){
        ChallengeDetailResponseDto challengeDetailResponseDto = challengeService.findChallengeById(challenge_id);
        return ResponseEntity.ok(challengeDetailResponseDto);
    }

    @DeleteMapping("/{challenge_id}")
    public ResponseEntity<?> deleteChallenge(
            @PathVariable("challenge_id") Long challenge_id
    ){
        challengeService.deleteChallenge(challenge_id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("msg", "챌린지를 성공적으로 삭제했습니다."));
    }
}