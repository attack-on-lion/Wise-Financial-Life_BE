package com.wisespendinglife.wise_spending_life.domain.store.dto;
import com.wisespendinglife.wise_spending_life.domain.store.entity.StoreEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class StoreResponseDTO {
    private String storeName; //상점 이름

    private String category; //카테고리 한글명 반환 위함

    public static StoreResponseDTO from(StoreEntity store) {
        return StoreResponseDTO.builder()
                .storeName(store.getStoreName())
                .category(store.getCategory().getName())
                .build();
    }
}
