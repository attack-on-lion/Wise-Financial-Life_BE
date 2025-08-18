package com.wisespendinglife.wise_spending_life.domain.user.ownership.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
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
}
