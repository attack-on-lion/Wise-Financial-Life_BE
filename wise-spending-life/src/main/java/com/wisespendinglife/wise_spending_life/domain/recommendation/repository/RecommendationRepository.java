package com.wisespendinglife.wise_spending_life.domain.recommendation.repository;

import com.wisespendinglife.wise_spending_life.domain.recommendation.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findAll();
    List<Recommendation> findByChallengeDays(Long challengeDays);
}
