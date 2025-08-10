package com.wisespendinglife.wise_spending_life.domain.solution.service;

import com.wisespendinglife.wise_spending_life.domain.solution.dto.SimpleSolutionResponseDTO;


public interface SolutionService {
    SimpleSolutionResponseDTO getSimpleSolution(Long userId);
}
