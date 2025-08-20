package com.wisespendinglife.wise_spending_life.domain.recommendation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_id")
    private Recommendation recommendation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    @Builder
    public RecommendationCategory(Recommendation recommendation, Category category) {
        this.recommendation = recommendation;
        this.category = category;
    }
}
