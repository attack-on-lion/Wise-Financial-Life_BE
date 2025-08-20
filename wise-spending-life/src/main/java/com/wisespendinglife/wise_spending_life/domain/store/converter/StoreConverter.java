package com.wisespendinglife.wise_spending_life.domain.store.converter;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.store.entity.StoreEntity;
import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreCategory;

public class StoreConverter {

    // Entity -> ResponseDTO
    public static StoreResponseDTO toStoreResponseDTO(StoreEntity entity) {
        return StoreResponseDTO.of(
                entity.getId(),
                entity.getStoreName(),
                entity.getLogoUrl(),
                entity.getCategory().getId(),
                StoreCategory.valueOf(entity.getCategory().getName())
        );
    }

    // RequestDTO -> Entity
    public static StoreEntity toEntity(StoreRequestDTO dto, Category category) {
        StoreEntity entity = new StoreEntity();
        entity.updateBasics(dto.getStoreName(), dto.getLogoUrl()); // 이름/로고 세팅
        entity.changeCategory(category); // FK 세팅
        return entity;
    }
}
