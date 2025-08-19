package com.wisespendinglife.wise_spending_life.domain.recommendation.service;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.category.entity.CategoryType;
import com.wisespendinglife.wise_spending_life.domain.category.repository.CategoryRepository;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeType;
import com.wisespendinglife.wise_spending_life.domain.recommendation.dto.*;
import com.wisespendinglife.wise_spending_life.domain.recommendation.entity.Recommendation;
import com.wisespendinglife.wise_spending_life.domain.recommendation.entity.RecommendationCategory;
import com.wisespendinglife.wise_spending_life.domain.recommendation.repository.RecommendationCategoryRepository;
import com.wisespendinglife.wise_spending_life.domain.recommendation.repository.RecommendationRepository;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import com.wisespendinglife.wise_spending_life.domain.user.repository.UserRepository;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");

    private final OpenAIRecommendationClient llm;
    private final RecommendationRepository recommendationRepository;
    private final RecommendationCategoryRepository recommendationCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public RecommendationCreateResponseDto generateRecommendation(RecommendationCreateRequestDto recommendationCreateRequestDto) {
        Long userId = recommendationCreateRequestDto.getUser_id();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        List<PaymentMiniDto> pays = recommendationCreateRequestDto.getPays();

        if (pays == null || pays.isEmpty()) {
            throw new BusinessException(ErrorCode.PAYMENT_NOT_FOUND);
        }

        List<Map<String, Object>> payMaps = pays.stream()
                .map(p -> Map.<String, Object>of(
                        "transactionAt", String.valueOf(p.getTransactionAt()), // LocalDate → "yyyy-MM-dd"
                        "category", p.getCategory() == null ? "" : p.getCategory().trim()
                ))
                .collect(Collectors.toList());

        var llmRes = llm.generate(userId, payMaps, null);
        var today = LocalDate.now(SEOUL);

        List<RecommendationItemDto> itemDtos = new ArrayList<>();
        for (var item : llmRes.recommendations()) {
            Set<Category> cats = item.categories().stream()
                    .map(name -> categoryRepository
                            .findByNameAndType(name, CategoryType.PAYMENT)
                            .orElseGet(() -> categoryRepository.save(
                                    Category.builder()
                                            .name(name)
                                            .type(CategoryType.PAYMENT)
                                            .build()
                            )))
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            ChallengeType type = item.challengeType();
            if (type == null) {
                throw new BusinessException(ErrorCode.INVALID_CHALLENGE_TYPE);
            }

            Recommendation rec = Recommendation.builder()
                    .user(user)
                    .challengeName(item.challengeName())
                    .challengeType(type)
                    .challengeDays(item.challengeDays())
                    .createdAt(today)
                    .build();
            rec = recommendationRepository.save(rec);

            for (Category c : cats) {
                recommendationCategoryRepository.save(
                        RecommendationCategory.builder()
                                .recommendation(rec)
                                .category(c)
                                .build()
                );
            }

            itemDtos.add(RecommendationItemDto.builder()
                    .id(rec.getId())
                    .user_id(userId)
                    .challengeName(rec.getChallengeName())
                    .challengeType(rec.getChallengeType())
                    .challengeDays(rec.getChallengeDays())
                    .createdAt(rec.getCreatedAt())
                    .categories(item.categories())
                    .build());
        }

        return RecommendationCreateResponseDto.builder()
                .user_id(userId)
                .recommendations(itemDtos)
                .build();
    }

    @Override
    public RecommendationDetailResponseDto getById(Long recommendationId) {
        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow( () -> new BusinessException(ErrorCode.RECOMMENDATION_NOT_FOUND));

        return RecommendationDetailResponseDto.builder()
                .id(recommendation.getId())
                .challengeName(recommendation.getChallengeName())
                .challengeType(recommendation.getChallengeType())
                .challengeDays(recommendation.getChallengeDays())
                .createdAt(recommendation.getCreatedAt())
                .build();
    }
}
