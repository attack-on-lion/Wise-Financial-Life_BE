package com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userGifticon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class UserGiftRequestDto {

    /**
     * 소유 추가 요청
     * - userId는 URL 경로에 두는 경우가 많아 보통 본 DTO에는 gifticonId만 받습니다.
     * - 필요 시 usedAt을 함께 넣어 소급 등록도 가능.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequest {
        @Schema(description = "등록할 기프티콘 ID", example = "123")
        private Long gifticon_id;
    }

    /**
     * 사용 처리 요청
     * - usedAt을 명시하지 않으면 서비스 레이어에서 now()로 처리하도록 해도 됩니다.
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UseRequest {
        private Long gifticon_id;
    }

}
