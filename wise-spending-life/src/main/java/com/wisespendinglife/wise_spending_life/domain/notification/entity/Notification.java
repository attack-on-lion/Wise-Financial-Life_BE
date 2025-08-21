package com.wisespendinglife.wise_spending_life.domain.notification.entity;


import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "notification",
        indexes = {
                @Index(
                        name = "idx_notification_user_created",
                        columnList = "user_id, createdAt DESC"
                ),
                @Index(
                        name = "idx_notification_category_created",
                        columnList = "category_id, createdAt DESC"
                ),
                @Index(
                        name = "idx_notification_user_not_deleted",
                        columnList = "user_id"
                )
        }
)
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(length = 255)
    private String deeplink;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    public boolean updateIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;

        return true;
    }
}