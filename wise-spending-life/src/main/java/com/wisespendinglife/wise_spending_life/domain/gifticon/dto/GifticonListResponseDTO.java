package com.wisespendinglife.wise_spending_life.domain.gifticon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class GifticonListResponseDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Cursor {
        private Long lastId;
        private LocalDateTime lastCreatedAt;
    }

    private List<GifticonResponseDTO> gifticonlist; // 실제 데이터
    private Cursor nextCursor;               // 다음 요청에 사용할 커서
    private boolean hasNext;                 // 다음 페이지 존재 여부
    private int size;                        // 요청 페이지 크기(응답에 복사)


}
