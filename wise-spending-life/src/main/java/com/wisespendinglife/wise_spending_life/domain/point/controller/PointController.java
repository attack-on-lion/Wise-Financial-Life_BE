package com.wisespendinglife.wise_spending_life.domain.point.controller;

import com.wisespendinglife.wise_spending_life.domain.point.dto.PointResponseDto;
import com.wisespendinglife.wise_spending_life.domain.point.service.PointServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{user_id}/points")
@RequiredArgsConstructor
public class PointController {

    private final PointServiceImpl pointService;

    @GetMapping("/ledger")
    public ResponseEntity<PointResponseDto.PointListResponseDto> getPointLedger(
            @PathVariable("user_id") Long userId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "15") int size
            ) {

        PointResponseDto.PointListResponseDto pointLedger = pointService.getPointLedger(userId, page, size);

        return ResponseEntity.ok(pointLedger);

    }
}
