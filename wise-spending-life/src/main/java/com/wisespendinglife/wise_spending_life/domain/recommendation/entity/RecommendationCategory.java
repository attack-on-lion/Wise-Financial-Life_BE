package com.wisespendinglife.wise_spending_life.domain.recommendation.entity;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "recommendation_category")
public class RecommendationCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_id")
    private Recommendation recommendation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public RecommendationCategory(Category category) {
        this.category = category;
    }

    void setRecommendationCategory(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    @Builder
    public RecommendationCategory(Recommendation recommendation, Category category) {
        this.recommendation = recommendation;
        this.category = category;
    }
}
