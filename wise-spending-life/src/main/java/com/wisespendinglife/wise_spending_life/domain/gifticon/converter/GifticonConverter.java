package com.wisespendinglife.wise_spending_life.domain.gifticon.converter;

import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.gifticon.entity.GifticonEntity;
import com.wisespendinglife.wise_spending_life.domain.store.entity.StoreEntity;


public class GifticonConverter {

    //Entity -> ResponseDTO //전체 기프티콘 정보 전달
    public static GifticonResponseDTO togifticonResponseDTO (GifticonEntity entity) {
        return GifticonResponseDTO.builder()
                .name(entity.getName())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .createdAt(entity.getCreatedAt())
                .expiredAt(entity.getExpiredAt())
                .isRecommend(entity.getIsRecommend())
                .isDeleted(entity.getIsDeleted())
                .build();
    }

    // toEntity
    public static GifticonEntity toEntity(GifticonRequestDTO dto, StoreEntity store) {
        return GifticonEntity.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .expiredAt(dto.getExpiredAt())
                .isRecommend(dto.getIsRecommend())
                .store(store)
                .build();
    }
}