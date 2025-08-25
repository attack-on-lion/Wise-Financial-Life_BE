package com.wisespendinglife.wise_spending_life.domain.recommendation.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeType;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;


    @Builder
    public Recommendation(User user, String challengeName, ChallengeType challengeType, Long challengeDays, LocalDateTime createdAt, List<RecommendationCategory> recommendationCategories) {
        this.user = user;
        this.challengeName = challengeName;
        this.challengeType = challengeType;
        this.challengeDays = challengeDays;
        this.createdAt = createdAt;
    }
}
