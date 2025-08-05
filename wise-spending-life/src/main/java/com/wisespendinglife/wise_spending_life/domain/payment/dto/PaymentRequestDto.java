package com.wisespendinglife.wise_spending_life.domain.payment.dto;

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
        private LocalDateTime transactionAt;

        @NotBlank(message = "{INVALID_INPUT_VALUE}")
        @Size(max = 100, message = "{INVALID_INPUT_VALUE}")
        private String storeName;

        @Positive(message = "{INVALID_AMOUNT}")
        private long amount;

        @NotNull(message = "{INVALID_RESOURCE_STATE}")
        private PaymentDirection direction;

        @NotNull(message = "{INVALID_PAYMENT_TYPE_REQUEST}")
        private PaymentMethod method;

        @NotBlank(message = "{INVALID_CATEGORY_REQUEST}")
        private String category;
    }


}
