package com.wisespendinglife.wise_spending_life.domain.point.dto;

import com.wisespendinglife.wise_spending_life.domain.point.entity.SourceKind;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


public class PointRequestDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EarnPointRequestDto implements PointDeltaRequest {

        @NotNull(message = "{INVALID_SOURCE_KIND}")
        @Schema(description = "포인트 출처")
        private SourceKind sourceKind;

        @NotNull(message = "{INVALID_AMOUNT_VALUE}")
        @Positive(message = "{INVALID_AMOUNT}")
        private Long delta;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SpendPointRequestDto implements PointDeltaRequest {

        @NotNull(message = "{INVALID_SOURCE_KIND}")
        @Schema(description = "포인트 출처")
        private SourceKind sourceKind;

        @NotNull(message = "{INVALID_AMOUNT_VALUE}")
        @Negative(message = "{INVALID_AMOUNT}")
        private Long delta;
    }

}
