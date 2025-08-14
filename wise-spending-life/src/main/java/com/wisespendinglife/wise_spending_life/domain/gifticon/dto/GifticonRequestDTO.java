package com.wisespendinglife.wise_spending_life.domain.gifticon.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class GifticonRequestDTO {
    @NotBlank(message = "{INVALID_STORE_NOT_NAME}")
    private String storeName;

    @NotBlank(message = "{INVALID_GIFTICON_NAME}")
    private String name; //제품명

    @NotNull(message = "{INVALID_GIFTICON_PRICE}")
    @Positive(message = "{INVALID_GIFTICON_PRICE_POSITIVE}") //값이 양수 검증 예외처리
    private Long price; //가격

    @NotBlank(message = "{INVALID_GIFTICON_IMAGE_URL}")
    private String imageUrl; //이미지 URL

    private Boolean isRecommend; //상품 추천 여부
}
