package com.wisespendinglife.wise_spending_life.domain.score.repository;

import com.wisespendinglife.wise_spending_life.domain.score.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findTopByOrderByCreatedAtDesc();
}
