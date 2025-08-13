package com.wisespendinglife.wise_spending_life.domain.point.entity;

import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name="point_ledger")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private SourceKind sourceKind;

    private Long delta;

    private Long balance;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
