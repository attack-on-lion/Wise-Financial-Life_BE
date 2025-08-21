package com.wisespendinglife.wise_spending_life.domain.user.ownership.entity;

import com.wisespendinglife.wise_spending_life.domain.gifticon.entity.GifticonEntity;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(
        name = "user_gifticon",
        indexes = {
                @Index(name = "idx_user_gifticon__user", columnList = "user_id"),
                @Index(name = "idx_user_gifticon__gifticon", columnList = "gifticon_id"),
                @Index(name = "idx_user_gifticon__created_at", columnList = "created_at")
        }

        , uniqueConstraints = @UniqueConstraint(name = "uk_user_gifticon__user_gifticon", columnNames = {"user_id", "gifticon_id"})
)
public class UserGifticon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK: user_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // FK: gifticon_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gifticon_id", nullable = false)
    private GifticonEntity gifticon;

    // 생성 시각
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 사용 시각 (미사용이면 null)
    private LocalDateTime usedAt;

    public void updateUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }
}