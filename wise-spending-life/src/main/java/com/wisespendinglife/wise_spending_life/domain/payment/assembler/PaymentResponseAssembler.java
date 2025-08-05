package com.wisespendinglife.wise_spending_life.domain.payment.assembler;

import com.wisespendinglife.wise_spending_life.domain.payment.converter.PaymentConverter;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentDirection;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentResponseAssembler {

    private final PaymentConverter converter;

    public PaymentResponseDto.Payments assemble(LocalDate from,
                                       LocalDate to,
                                       Page<Payment> page) {

        // 1) summary 계산
        long totalExpense = page.stream()
                .filter(p -> p.getDirection() == PaymentDirection.OUTFLOW)
                .mapToLong(Payment::getAmount)
                .sum();

        long totalIncome  = page.stream()
                .filter(p -> p.getDirection() == PaymentDirection.INFLOW)
                .mapToLong(Payment::getAmount)
                .sum();

        var summary = PaymentResponseDto.Summary.builder()
                .period(PaymentResponseDto.Period.builder()
                        .from(from)
                        .to(to)
                        .build())
                .totalExpense(totalExpense)
                .totalIncome(totalIncome)
                .count((int) page.getTotalElements())
                .build();

        // 2) pageInfo 조립
        var pageInfo = PaymentResponseDto.PageInfo.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .hasNext(page.hasNext())
                .build();

        // 3) item 리스트 변환
        List<PaymentResponseDto.Item> items =
                converter.toItemDtos(page.getContent());

        // 4) 최종 DTO 반환
        return PaymentResponseDto.Payments.builder()
                .summary(summary)
                .pageInfo(pageInfo)
                .items(items)
                .build();
    }
}