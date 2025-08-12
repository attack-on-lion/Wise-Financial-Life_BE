package com.wisespendinglife.wise_spending_life.domain.gifticon.dto;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class GifticonRequestDTO {
    @NotNull(message = "{INVALID_STORE_ID}")
    @Positive(message = "{INVALID_STORE_ID_POSITIVE}")
    private Long storeId;

    @NotBlank(message = "{INVALID_GIFTICON_NAME}")
    private String name; //제품명

    @NotNull(message = "{INVALID_GIFTICON_PRICE}")
    @Positive(message = "{INVALID_GIFTICON_PRICE_POSITIVE}") //값이 양수 검증 예외처리
    private Long price; //가격

    @NotBlank(message = "{INVALID_GIFTICON_IMAGE_URL}")
    private String imageUrl; //이미지 URL

    @NotNull(message = "{INVALID_GIFTICON_EXPIRED_AT}")
    @Future(message = "{INVALID_GIFTICON_EXPIRED_AT_FUTURE}") //값이 현재 시각 이후 예외처리
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredAt; //만료 시간

    @NotNull(message = "{INVALID_GIFTICON_IS_RECOMMEND}")
    private Boolean isRecommend; //상품 추천 여부
}
