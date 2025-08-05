package com.wisespendinglife.wise_spending_life.domain.payment.entity;

/*
 * PaymentResponseDto
 *
 * @author holychann
 * @since 2025-08-05
 */

import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentDirection;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime transactionAt;
    private String storeName;
    private long amount;

    @Enumerated(EnumType.STRING)
    private PaymentDirection direction;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
}
