package com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userGifticon;

import lombok.*;

public class UserGiftRequestDto {

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
