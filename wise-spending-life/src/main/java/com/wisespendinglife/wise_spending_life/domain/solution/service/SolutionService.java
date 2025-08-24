package com.wisespendinglife.wise_spending_life.domain.solution.service;

import com.wisespendinglife.wise_spending_life.domain.solution.dto.SimpleSolutionResponseDTO;


public interface SolutionService {
    SimpleSolutionResponseDTO getMonthlyComparisonSolution(Long userId);
    SimpleSolutionResponseDTO getSimpleSolutionMonthly(Long userId);
    SimpleSolutionResponseDTO getWeeklySolution(Long userId);
}
