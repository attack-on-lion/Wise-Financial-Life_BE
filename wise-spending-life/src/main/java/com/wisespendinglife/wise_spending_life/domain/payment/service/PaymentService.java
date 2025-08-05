package com.wisespendinglife.wise_spending_life.domain.payment.service;

import com.wisespendinglife.wise_spending_life.domain.payment.assembler.PaymentResponseAssembler;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.payment.entity.Payment;
import com.wisespendinglife.wise_spending_life.domain.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentResponseAssembler responseAssembler;

    public PaymentResponseDto getMonthly(LocalDate from, LocalDate to, int currentPage, int size) {

        // Pageable 설정
        Pageable pageable = PageRequest.of(currentPage, size, Sort.by("transactionAt").descending());

        // Page 조회
        Page<Payment> page = paymentRepository.findByTransactionAtBetween(from, to, pageable);

        // Response 생성
        return responseAssembler.assemble(from, to, page);   // ← 한 줄
    }

}
