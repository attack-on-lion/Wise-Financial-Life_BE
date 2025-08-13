package com.wisespendinglife.wise_spending_life.domain.point.dto;

import com.wisespendinglife.wise_spending_life.domain.point.entity.SourceKind;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;


public class PointRequestDto {

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EarnPointRequestDto implements PointDeltaRequest {

        @NotNull(message = "{INVALID_SOURCE_KIND}")
        @Schema(description = "포인트 출처")
        private SourceKind sourceKind;

        @Nullable
        private Long challengeId;

        @NotNull(message = "{INVALID_AMOUNT_VALUE}")
        @Positive(message = "{INVALID_AMOUNT}")
        private Long delta;
    }

    @Builder
    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SpendPointRequestDto implements PointDeltaRequest {

        @NotNull(message = "{INVALID_SOURCE_KIND}")
        @Schema(description = "포인트 출처")
        private SourceKind sourceKind;

        /**
         * 코드 양 줄이기위한 필드... 항상 NULL
         */
        private Long challengeId;

        @NotNull(message = "{INVALID_AMOUNT_VALUE}")
        @Negative(message = "{INVALID_AMOUNT}")
        private Long delta;
    }

}
