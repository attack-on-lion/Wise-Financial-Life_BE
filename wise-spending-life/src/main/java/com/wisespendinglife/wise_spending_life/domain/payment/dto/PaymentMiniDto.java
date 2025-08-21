package com.wisespendinglife.wise_spending_life.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentMiniDto {
    @NotNull(message = "{INVALID_DATE_REQUEST}")
    @Schema(description = "거래 일시", example = "2025-07-09")
    private LocalDateTime transactionAt;

    @NotBlank(message = "{INVALID_CATEGORY_REQUEST}")
    @Schema(description = "카테고리", example = "식비")
    private String category;
}
