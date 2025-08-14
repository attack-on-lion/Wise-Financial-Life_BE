package com.wisespendinglife.wise_spending_life.domain.payment.converter;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentRequestDto;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.payment.entity.Payment;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
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

    public Payment toEntity(PaymentRequestDto.CreateDto dto, Category category, User user) {
        return Payment.builder()
                .transactionAt(dto.getTransactionAt())
                .storeName(dto.getStoreName())
                .amount(dto.getAmount())
                .direction(dto.getDirection())
                .method(dto.getMethod())
                .user(user)
                .category(category)
                .build();
    }

    public PaymentResponseDto.PaymentCreateResponseDto toCreateResponseDto(Long id) {
        return PaymentResponseDto.PaymentCreateResponseDto.builder()
                .id(id)
                .build();
    }


}
