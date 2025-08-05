package com.wisespendinglife.wise_spending_life.domain.payment.controller;

import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentRequestDto;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.payment.service.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Validated   // 파라미터 검증 애노테이션 사용 가능
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    @GetMapping
    public ResponseEntity<PaymentResponseDto.Payments> findPaymentsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "15") int size
            ) {

        PaymentResponseDto.Payments dto = paymentService.getMonthly(from, to, page, size);

        return ResponseEntity.ok(dto);
    }
    

    @PostMapping
    public ResponseEntity<PaymentResponseDto.PaymentCreateResponseDto> createPayment(
            @Validated @RequestBody PaymentRequestDto.CreateDto dto) {

        PaymentResponseDto.PaymentCreateResponseDto result = paymentService.create(dto);
        return ResponseEntity.ok(result);
    }

}
