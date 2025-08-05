package com.wisespendinglife.wise_spending_life.domain.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

        @NotNull
        private LocalDateTime transactionAt;

        @NotBlank
        private String storeName;

        @Positive
        private long amount;

        @NotNull
        private PaymentDirection direction;

        @NotNull
        private PaymentMethod method;

        @NotNull
        private String category;
    }


}
