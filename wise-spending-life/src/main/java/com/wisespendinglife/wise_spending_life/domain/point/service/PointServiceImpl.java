package com.wisespendinglife.wise_spending_life.domain.point.service;

import com.wisespendinglife.wise_spending_life.domain.point.assembler.PointAssembler;
import com.wisespendinglife.wise_spending_life.domain.point.dto.PointResponseDto;
import com.wisespendinglife.wise_spending_life.domain.point.entity.Point;
import com.wisespendinglife.wise_spending_life.domain.point.repository.PointLedgerRepository;
import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
import com.wisespendinglife.wise_spending_life.domain.user.repository.UserRepository;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PointServiceImpl implements PointService {

    PointLedgerRepository pointLedgerRepository;
    UserRepository userRepository;
    PointAssembler pointAssembler;

    @Override
    public PointResponseDto.PointListResponseDto getPointLedger(Long userId, int page, int size) {

        // 유저 유효성 검사
        userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.desc("createdAt"),
                Sort.Order.desc("id")
        ));

        Page<Point> result = pointLedgerRepository.findByUserId(userId, pageable);

        return pointAssembler.assemble(
                result.getTotalElements(),
                page,
                size,
                result.hasNext(),
                result.getContent()
        );

    }
}
