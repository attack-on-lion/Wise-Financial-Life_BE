package com.wisespendinglife.wise_spending_life.domain.payment.entity;
import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentDirection;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentMethod;
import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // 단방향 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
