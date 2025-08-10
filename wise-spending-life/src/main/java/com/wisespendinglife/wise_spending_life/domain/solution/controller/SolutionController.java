package com.wisespendinglife.wise_spending_life.domain.solution.controller;

import com.wisespendinglife.wise_spending_life.domain.solution.dto.SimpleSolutionResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.solution.service.SolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/ai")
@RequiredArgsConstructor
public class SolutionController {

    private final SolutionService solutionService;

    @PostMapping("/simple-solution")
    public ResponseEntity<SimpleSolutionResponseDTO> aiSimpleSolution(@PathVariable Long userId) {
        return ResponseEntity.ok(solutionService.getSimpleSolution(userId));
    }

}
