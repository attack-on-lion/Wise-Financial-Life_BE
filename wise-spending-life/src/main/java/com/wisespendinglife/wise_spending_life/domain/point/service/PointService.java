package com.wisespendinglife.wise_spending_life.domain.point.service;

import com.wisespendinglife.wise_spending_life.domain.point.dto.PointResponseDto;

public interface PointService {

    /**
     * 포인트 내역 반환 API
     *
     * @param userId 반환 할 유저의 아이디
     * @param page 현재 페이지
     * @param size 사이즈
     * @return summary, pageInfo, items 를 포함한 DTO
     */
    PointResponseDto.PointListResponseDto getPointLedger(Long userId, int page, int size);

}
