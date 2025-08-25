package com.wisespendinglife.wise_spending_life.domain.challenge.entity;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "challenge_category")
public class ChallengeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public ChallengeCategory(Category category) {
        this.category = category;
    }

    void setChallengeCategory(Challenge challenge) {
        this.challenge = challenge;
    }
}