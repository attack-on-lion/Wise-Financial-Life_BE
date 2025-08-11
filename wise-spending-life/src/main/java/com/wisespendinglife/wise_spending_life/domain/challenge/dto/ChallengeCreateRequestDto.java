package com.wisespendinglife.wise_spending_life.domain.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ChallengeCreateRequestDto {
    @NotNull(message = "{INVALID_USER_ID}")
    @Schema(description = "사용자 ID", example = "15")
    private Long user_id;
    @NotBlank(message = "{INVALID_CHALLENGE_NAME}")
    @Size(max = 50, message = "{INVALID_CHALLENGE_NAME}")
    @Schema(description = "챌린지 이름", example = "식비 줄이기 챌린지")
    private String challengeName;
    @NotNull(message = "{INVALID_CHALLENGE_TYPE}")
    @Schema(description = "챌린지 타입", example = "적게 쓰기")
    private ChallengeType challengeType;
    @NotNull(message = "{INVALID_CHALLENGE_DAYS}")
    @Schema(description = "챌린지 일수", example = "3")
    private Long challengeDays;

    @NotNull(message = "{INVALID_START_AT}")
    @Schema(description = "시작일", example = "2025-08-04")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt;
    @NotNull(message = "{INVALID_END_AT}")
    @Schema(description = "종료일", example = "2025-08-07")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAt;
    @NotNull(message = "{INVALID_CREATED_AT}")
    @Schema(description = "생성일", example = "2025-08-04")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @NotEmpty(message = "{INVALID_CATEGORIES}")
    @Schema(description = "카테고리 선택", example = "식비")
    private List<String> categories;

}
