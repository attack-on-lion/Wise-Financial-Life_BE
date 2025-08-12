package com.wisespendinglife.wise_spending_life.domain.gifticon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder

public class GifticonResponseDTO {
    private String name;
    private Long price;
    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    private Boolean isDeleted;
    private Boolean isRecommend;
}
