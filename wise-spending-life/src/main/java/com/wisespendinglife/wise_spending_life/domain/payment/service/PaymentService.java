package com.wisespendinglife.wise_spending_life.domain.payment.service;

import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentMiniDto;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentRequestDto;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.score.dto.ScoreResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/*
 * Payment Service Interface
 */
public interface PaymentService {

    PaymentResponseDto.Payments getMonthly(LocalDate from,
                                           LocalDate to,
                                           int currentPage,
                                           int size,
                                           Long userId,
                                           Optional<String> categoryOpt);

    PaymentResponseDto.PaymentCreateResponseDto create(PaymentRequestDto.CreateDto dto, Long userId);

    /**
     * 전월 결제 내역을 집계해 ChatGPT 점수를 산출한다.
     * @param userId 사용자 ID
     * @return 0-100 점수
     */
    ScoreResponseDto calculateMonthlyScore(Long userId);

    List<PaymentMiniDto> getPaymentMiniList(Long userId, LocalDateTime from, LocalDateTime to);
}
