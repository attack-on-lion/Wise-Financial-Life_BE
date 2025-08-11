package com.wisespendinglife.wise_spending_life.domain.challenge.service;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.category.repository.CategoryRepository;
import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ChallengeCreateRequestDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ChallengeDetailResponseDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.dto.ValidChallengeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.Challenge;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeCategory;
import com.wisespendinglife.wise_spending_life.domain.challenge.repository.ChallengeRepository;
import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
import com.wisespendinglife.wise_spending_life.domain.user.repository.UserRepository;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Challenge createChallenge(ChallengeCreateRequestDto dto) {
        UserEntity user = userRepository.findById(dto.getUser_id())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Challenge newchallenge = Challenge.builder()
                .user(user)
                .challengeName(dto.getChallengeName())
                .challengeType(dto.getChallengeType())
                .challengeDays(dto.getChallengeDays())
                .startAt(dto.getStartAt())
                .endAt(dto.getEndAt())
                .createdAt(dto.getCreatedAt())
                .characterImageUrl("url") // url 설정하기 (필요함)
                .isCompleted(false) // 미완료로 자동 세팅
                .isDeleted(false) // 미삭제로 자동 세팅
                .build();
        List<String> categoryNames = dto.getCategories();
        for (String categoryName : categoryNames) {
            Category category = categoryRepository.findByName(categoryName)
                    .orElseThrow(()-> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
            newchallenge.addChallengeCategory(new ChallengeCategory(category));
        }
        return challengeRepository.save(newchallenge);
    }

    @Override
    @Transactional(readOnly = true)
    public ValidChallengeResponseDto findValidChallenge(boolean isCompleted, boolean isDeleted) {
        return challengeRepository.findByIsCompletedAndIsDeleted(isCompleted, isDeleted)
                .map(ValidChallengeResponseDto::new)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHALLENGE_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public ChallengeDetailResponseDto findChallengeById(Long challenge_id) {
        Challenge challenge = challengeRepository.findById(challenge_id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CERTAIN_CHALLENGE_NOT_FOUND));
        return new ChallengeDetailResponseDto(challenge);
    }

    @Override
    @Transactional
    public void deleteChallenge(Long challenge_id) {
        Challenge challenge = challengeRepository.findById(challenge_id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CERTAIN_CHALLENGE_NOT_FOUND));
        // 삭제된 것으로 처리
        challenge.setIsDeleted();
        challengeRepository.save(challenge);
        // 물리 삭제
//        challengeRepository.delete(challenge);
    }
}
