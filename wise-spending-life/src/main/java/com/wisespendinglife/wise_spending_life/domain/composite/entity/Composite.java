package com.wisespendinglife.wise_spending_life.domain.composite.entity;

import com.wisespendinglife.wise_spending_life.domain.character.entity.Character;
import com.wisespendinglife.wise_spending_life.domain.item.entity.Item;
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
        name = "composite_asset",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_composite_char_item",
                        columnNames = {"character_id", "item_id"}
                )
        },
        indexes = {
                @Index(name = "idx_composite_character", columnList = "character_id"),
                @Index(name = "idx_composite_item", columnList = "item_id")
        }
)
public class Composite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "character_id",
            nullable = false
    )
    private Character character;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "item_id",
            nullable = false
    )
    private Item item;

    private String imageUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
