package com.wisespendinglife.wise_spending_life.domain.recommendation.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentMiniDto {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionAt;

    @NotBlank
    private String category;
}
