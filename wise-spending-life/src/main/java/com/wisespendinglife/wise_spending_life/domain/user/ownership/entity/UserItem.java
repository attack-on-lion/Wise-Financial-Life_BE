package com.wisespendinglife.wise_spending_life.domain.user.ownership.entity;

import com.wisespendinglife.wise_spending_life.domain.item.entity.Item;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@ToString
@Table(
        name = "user_item",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_item_user_item",
                        columnNames = {"user_id", "item_id"}
                )
        },
        indexes = {
                @Index(name = "idx_user_item_user", columnList = "user_id"),
                @Index(name = "idx_user_item_item", columnList = "item_id")
        }
)
public class UserItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_item_user")
    )
    private User user;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "item_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_item_item")
    )
    private Item item;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
