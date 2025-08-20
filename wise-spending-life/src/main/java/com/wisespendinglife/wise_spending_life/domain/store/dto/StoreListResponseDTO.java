package com.wisespendinglife.wise_spending_life.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class StoreListResponseDTO {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Cursor {
        private String lastStoreName; //가나다순
        private Long lastId;
    }
    private List<StoreResponseDTO> stores; // 실제 데이터
    private StoreListResponseDTO.Cursor nextCursor;               // 다음 요청에 사용할 커서
    private boolean hasNext;                 // 다음 페이지 존재 여부
    private int size;                        // 요청 페이지 크기(응답에 복사)
}
