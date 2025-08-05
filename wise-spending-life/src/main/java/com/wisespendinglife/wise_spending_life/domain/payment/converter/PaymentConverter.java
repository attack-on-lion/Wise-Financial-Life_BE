package com.wisespendinglife.wise_spending_life.domain.payment.converter;

import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * Payment Converter
 */

@Component
@RequiredArgsConstructor
public class PaymentConverter {

    // Entity -> DTO(Item)
    public PaymentResponseDto.Item toItemDto(Payment e) {
        return PaymentResponseDto.Item.builder()
                .id(e.getId())
                .transactionAt(e.getTransactionAt())
                .storeName(e.getStoreName())
                .amount(e.getAmount())
                .direction(e.getDirection())
                .method(e.getMethod())
                .category(
                        PaymentResponseDto.Category.builder()
                                .id(e.getCategory().getId())
                                .name(e.getCategory().getName())
                                .build())
                .build();
    }

    /**
     * Payment Entity List -> DTO List(Item)
     * @param list
     * @return
     */
    public List<PaymentResponseDto.Item> toItemDtos(List<Payment> list) {
        return list.stream()
                .map(this::toItemDto)
                .toList();
    }

}
