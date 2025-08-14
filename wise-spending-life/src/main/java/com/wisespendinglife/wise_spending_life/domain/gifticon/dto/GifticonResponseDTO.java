package com.wisespendinglife.wise_spending_life.domain.gifticon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder

public class GifticonResponseDTO {
    private Long id;
    private String name;
    private Long price;
    private String imageUrl;

    private LocalDateTime createdAt;
    private Boolean isRecommend;
}
