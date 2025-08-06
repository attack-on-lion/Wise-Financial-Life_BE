package com.wisespendinglife.wise_spending_life.domain.payment.service;

import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentRequestDto;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;

import java.time.LocalDate;
import java.util.Optional;

/*
 * Payment Service Interface
 */
public interface PaymentService {

    PaymentResponseDto.Payments getMonthly(LocalDate from,
                                           LocalDate to,
                                           int currentPage,
                                           int size,
                                           Optional<String> categoryOpt);

    public PaymentResponseDto.PaymentCreateResponseDto create(PaymentRequestDto.CreateDto dto);

}
