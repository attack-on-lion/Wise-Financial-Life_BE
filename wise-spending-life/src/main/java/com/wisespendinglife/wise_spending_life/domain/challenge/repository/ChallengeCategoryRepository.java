package com.wisespendinglife.wise_spending_life.domain.challenge.repository;

import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeCategoryRepository extends JpaRepository<ChallengeCategory, Long> {
}
