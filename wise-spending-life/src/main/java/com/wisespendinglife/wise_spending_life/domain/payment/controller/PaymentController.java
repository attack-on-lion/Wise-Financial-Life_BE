package com.wisespendinglife.wise_spending_life.domain.payment.controller;

import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentRequestDto;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.payment.service.PaymentServiceImpl;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Validated   // 파라미터 검증 애노테이션 사용 가능
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    @GetMapping("/{userId}")
    public ResponseEntity<PaymentResponseDto.Payments> findPaymentsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "15") int size,
            @PathVariable Long userId,
            @RequestParam(required = false) String category
            ) {

        PaymentResponseDto.Payments dto = paymentService.getMonthly(from, to, page, size, userId,Optional.ofNullable(category));

        return ResponseEntity.ok(dto);
    }


    
    @PostMapping("/{user_id}")
    public ResponseEntity<PaymentResponseDto.PaymentCreateResponseDto> createPayment(
            @PathVariable("user_id") Long userId,
            @Validated @RequestBody PaymentRequestDto.CreateDto dto) {

        PaymentResponseDto.PaymentCreateResponseDto result = paymentService.create(dto, userId);
        return ResponseEntity.ok(result);
    }

    /**
     * 이번 주 월요일 00:00 ~ 지금까지(Asia/Seoul) 일자별 총 지출 반환
     * - 지출 없는 날은 0으로 채워서 월~오늘까지 모두 포함
     */
    @GetMapping("/{user_id}/weekly")
    public ResponseEntity<PaymentResponseDto.WeeklyDailyTotals> getWeeklyDailyTotals(
            @PathVariable("user_id") Long userId
    ) {
        PaymentResponseDto.WeeklyDailyTotals body = paymentService.getWeeklyDailyTotals(userId);
        return ResponseEntity.ok(body);
    }

}
