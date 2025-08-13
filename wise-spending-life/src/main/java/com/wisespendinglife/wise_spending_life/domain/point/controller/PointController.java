package com.wisespendinglife.wise_spending_life.domain.point.controller;

import com.wisespendinglife.wise_spending_life.domain.point.dto.PointRequestDto;
import com.wisespendinglife.wise_spending_life.domain.point.dto.PointResponseDto;
import com.wisespendinglife.wise_spending_life.domain.point.service.PointServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    /**
     * 포인트 적립 컨트롤러
     *
     * @return
     */
    @PostMapping("/earn")
    public ResponseEntity<PointResponseDto.PointBalanceResponseDto> earnPoint(
            @PathVariable("user_id") Long userId,
            @Validated @RequestBody PointRequestDto.EarnPointRequestDto dto
    ){

        PointResponseDto.PointBalanceResponseDto pointBalanceResponseDto = pointService.handlePointChange(userId, dto);

        return ResponseEntity.ok(pointBalanceResponseDto);
    }

    /**
     * 포인트 사용 컨트롤러
     *
     * @return
     */
    @PostMapping("/spend")
    public ResponseEntity<PointResponseDto.PointBalanceResponseDto> spendPoint(
            @PathVariable("user_id") Long userId,
            @Validated @RequestBody PointRequestDto.SpendPointRequestDto dto
    ){
        PointResponseDto.PointBalanceResponseDto pointBalanceResponseDto = pointService.handlePointChange(userId, dto);

        return ResponseEntity.ok(pointBalanceResponseDto);
    }

}
