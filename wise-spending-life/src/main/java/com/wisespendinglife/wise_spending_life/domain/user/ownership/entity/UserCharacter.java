package com.wisespendinglife.wise_spending_life.domain.user.ownership.entity;

import com.wisespendinglife.wise_spending_life.domain.character.entity.Character;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Builder
@Table(
        name = "user_character",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_character_user_character",
                        columnNames = {"user_id", "character_id"}
                )
        },
        indexes = {
                @Index(name = "idx_user_character_user", columnList = "user_id"),
                @Index(name = "idx_user_character_character", columnList = "character_id")
        }
)
public class UserCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_character_id")
    private Long id;

    /**
     * User FK
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_character_user"))
    private User user;

    /**
     * Character FK
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_character_character"))
    private Character character;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;
}