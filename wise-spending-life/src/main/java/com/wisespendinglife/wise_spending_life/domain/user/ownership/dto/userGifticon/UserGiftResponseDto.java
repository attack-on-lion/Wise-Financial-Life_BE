package com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userGifticon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class UserGiftResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UseResponseDto {
        private boolean isSuccess;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PurchaseResponseDto {
        private boolean isSuccess;
        private Long remainingPoint;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OwnedGifticonListDto {
        private int size;
        private List<OwnedGifticonDto> ownedGifticonList;
    }

    /**
     * 단건 응답
     * - 중간 엔티티의 핵심 컬럼을 그대로 노출.
     * - 추후 확장(기프티콘 상세, 썸네일 등)이 필요하면 view 전용 DTO를 별도 분리해도 됩니다.
     */
    @Getter @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OwnedGifticonDto {

        @Schema(description = "user_gifticon PK", example = "1")
        private Long id;
        @Schema(description = "유저 ID", example = "1")
        private Long user_id;
        @Schema(description = "기프티콘 ID", example = "1")
        private Long gifticon_id;
        private String gifticonName;
        private Long gifticonPrice;
        private String imageUrl;
        @Schema(description = "사용 시각(미사용 시 null)", example = "2025-08-21T09:00:00")
        private LocalDateTime usedAt;
    }
}
