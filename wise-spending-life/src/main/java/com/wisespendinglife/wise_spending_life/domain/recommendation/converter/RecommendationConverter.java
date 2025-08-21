package com.wisespendinglife.wise_spending_life.domain.recommendation.converter;

import com.wisespendinglife.wise_spending_life.domain.recommendation.dto.RecommendationSummaryDto;
import com.wisespendinglife.wise_spending_life.domain.recommendation.entity.Recommendation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecommendationConverter {
    public RecommendationSummaryDto toSummary(Recommendation r) {
        return RecommendationSummaryDto.builder()
                .challengeName(r.getChallengeName())
                .challengeType(r.getChallengeType())
                .challengeDays(r.getChallengeDays())
                .build();
    }

    public List<RecommendationSummaryDto> toSummaryList(List<Recommendation> list) {
        return list.stream().map(this::toSummary).toList();
    }
}
