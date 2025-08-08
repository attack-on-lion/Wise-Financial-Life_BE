package com.wisespendinglife.wise_spending_life.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PaymentRequestDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CreateDto {

        @NotNull(message = "{INVALID_DATE_REQUEST}")
        @PastOrPresent(message = "{INVALID_DATE_REQUEST}")
        @Schema(description = "거래 일시", example = "2025-07-09T15:12:00+09:00")
        private LocalDateTime transactionAt;

        @NotBlank(message = "{INVALID_INPUT_VALUE}")
        @Size(max = 100, message = "{INVALID_INPUT_VALUE}")
        @Schema(description = "가게 이름", example = "가게 이름")
        private String storeName;

        @Positive(message = "{INVALID_AMOUNT}")
        @Schema(description = "가격", example = "10000")
        private long amount;

        @NotNull(message = "{INVALID_RESOURCE_STATE}")
        @Schema(description = "거래 방향", example = "INCOME")
        private PaymentDirection direction;

        @NotNull(message = "{INVALID_PAYMENT_TYPE_REQUEST}")
        @Schema(description = "결제 방법", example = "CARD")
        private PaymentMethod method;

        @NotBlank(message = "{INVALID_CATEGORY_REQUEST}")
        @Schema(description = "카테고리", example = "식비")
        private String category;
    }


}
