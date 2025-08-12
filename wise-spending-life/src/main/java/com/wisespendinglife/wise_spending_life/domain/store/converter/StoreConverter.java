package com.wisespendinglife.wise_spending_life.domain.store.converter;

import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.store.entity.StoreEntity;
import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
public class StoreConverter {

    public static StoreResponseDTO toStoreResponseDTO(StoreEntity entity) {
        return StoreResponseDTO.builder()
                .storeName(entity.getStoreName())
                .category(entity.getCategory().getName())
                .build();
    }

    public static StoreEntity toEntity(StoreResponseDTO dto, Category category) {
        return StoreEntity.builder()
                .storeName(dto.getStoreName())
                .category(category)
                .build();
    }
}
