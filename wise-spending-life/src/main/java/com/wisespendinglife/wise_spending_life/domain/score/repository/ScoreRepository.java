package com.wisespendinglife.wise_spending_life.domain.score.repository;

import com.wisespendinglife.wise_spending_life.domain.score.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
}
