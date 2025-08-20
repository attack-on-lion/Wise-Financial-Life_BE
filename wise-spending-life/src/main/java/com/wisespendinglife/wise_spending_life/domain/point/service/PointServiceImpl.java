package com.wisespendinglife.wise_spending_life.domain.point.service;

import com.wisespendinglife.wise_spending_life.domain.challenge.entity.Challenge;
import com.wisespendinglife.wise_spending_life.domain.challenge.repository.ChallengeRepository;
import com.wisespendinglife.wise_spending_life.domain.point.assembler.PointAssembler;
import com.wisespendinglife.wise_spending_life.domain.point.converter.PointConverter;
import com.wisespendinglife.wise_spending_life.domain.point.dto.PointDeltaRequest;
import com.wisespendinglife.wise_spending_life.domain.point.dto.PointResponseDto;
import com.wisespendinglife.wise_spending_life.domain.point.entity.Point;
import com.wisespendinglife.wise_spending_life.domain.point.repository.PointLedgerRepository;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
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

    private final PointConverter pointConverter;
    private final PointLedgerRepository pointLedgerRepository;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final PointAssembler pointAssembler;

    /**
     * 포인트를 추가/사용하는 메소드
     *
     * @param userId 유저 아이디(Path Variable)
     * @param dto PointDeltaRequest
     * @return balance 반환 Dto
     */
    @Override
    public PointResponseDto.PointBalanceResponseDto handlePointChange(Long userId, PointDeltaRequest dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Challenge challenge = null;
        if(dto.getChallengeId() != null) {
            challenge = challengeRepository.findById(dto.getChallengeId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.CHALLENGE_NOT_FOUND));
        }

        if(user.getPoint() + dto.getDelta() < 0){
            throw new BusinessException(ErrorCode.POINT_INSUFFICIENT);
        }

        Point entity = pointConverter.toEntity(dto, user, Optional.ofNullable(challenge));

        pointLedgerRepository.save(entity);

        user.updatePoint(user.getPoint() + dto.getDelta());  // 업데이트된 포인트 저장
        log.info(">>> [SERVICE] Updated Point, userId={} delta={} Balance={}", userId, dto.getDelta(), user.getPoint());

        return pointConverter.toPointBalanceDto(entity);
    }


    /**
     * 포인트 내역 조회하는 메소드
     *
     * @param userId 유저 아이디(Path Variable)
     * @param page 현재 페이지
     * @param size 사이즈
     * @return 반환용 Dto
     */
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

        log.info(">>> [SERVICE] Get PointLedger -> userId={} page={} size={} hasNext={}", userId, page, size,  result.hasNext());

        return pointAssembler.assemble(
                result.getTotalElements(),
                page,
                size,
                result.hasNext(),
                result.getContent()
        );

    }
}
