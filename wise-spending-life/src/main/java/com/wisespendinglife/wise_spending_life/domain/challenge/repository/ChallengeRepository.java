package com.wisespendinglife.wise_spending_life.domain.challenge.repository;

import com.wisespendinglife.wise_spending_life.domain.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
