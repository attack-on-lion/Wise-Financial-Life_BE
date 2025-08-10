package com.wisespendinglife.wise_spending_life.domain.solution.repository;

import com.wisespendinglife.wise_spending_life.domain.solution.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solution, Long> {
}
