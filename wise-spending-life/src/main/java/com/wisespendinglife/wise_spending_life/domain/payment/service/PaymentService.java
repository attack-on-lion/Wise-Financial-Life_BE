package com.wisespendinglife.wise_spending_life.domain.payment.service;

import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;

import java.time.LocalDate;

/*
 * Payment Service Interface
 */
public interface PaymentService {

    PaymentResponseDto getMonthly(LocalDate from, LocalDate to, int currentPage, int size);


}
