package com.wisespendinglife.wise_spending_life.domain.recommendation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class RecommendationCreateRequestDto {
    @NotNull(message = "{INVALID_USER_ID}")
    @Schema(description = "사용자 ID", example = "1")
    private Long user_id;

    @Size(min = 1)
    private List<PaymentMiniDto> pays;
}
