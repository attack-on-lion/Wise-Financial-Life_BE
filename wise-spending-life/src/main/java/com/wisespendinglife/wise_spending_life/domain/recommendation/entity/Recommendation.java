package com.wisespendinglife.wise_spending_life.domain.recommendation.entity;

import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeType;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "recommendation")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String challengeName;
    @Enumerated(EnumType.STRING)
    private ChallengeType challengeType;
    private Long challengeDays;
    private LocalDate createdAt;

    @OneToMany(mappedBy = "recommendation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecommendationCategory> recommendationCategories = new ArrayList<>();

    public void setRecommendationCategory(RecommendationCategory recommendationCategory) {
        this.recommendationCategories.add(recommendationCategory);
        recommendationCategory.setRecommendationCategory(this);
    }
    @Builder
    public Recommendation(User user, String challengeName, ChallengeType challengeType, Long challengeDays, LocalDate createdAt, List<RecommendationCategory> recommendationCategories) {
        this.user = user;
        this.challengeName = challengeName;
        this.challengeType = challengeType;
        this.challengeDays = challengeDays;
        this.createdAt = createdAt;
        this.recommendationCategories = recommendationCategories;
    }
}
