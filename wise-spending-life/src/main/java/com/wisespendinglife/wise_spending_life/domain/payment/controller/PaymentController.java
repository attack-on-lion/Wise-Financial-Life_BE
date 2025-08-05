package com.wisespendinglife.wise_spending_life.domain.payment.controller;

import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.payment.service.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Validated   // 파라미터 검증 애노테이션 사용 가능
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    @GetMapping
    public ResponseEntity<PaymentResponseDto> findPaymentsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "15") int size
            ) {

        PaymentResponseDto dto = paymentService.getMonthly(from, to, page, size);

        return ResponseEntity.ok(dto);
    }
}
