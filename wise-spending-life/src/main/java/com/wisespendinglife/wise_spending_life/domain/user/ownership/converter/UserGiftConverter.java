package com.wisespendinglife.wise_spending_life.domain.user.ownership.converter;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userGifticon.UserGiftResponseDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.entity.UserGifticon;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserGiftConverter {

    public UserGiftResponseDto.OwnedGifticonDto toDto(UserGifticon entity) {
        return UserGiftResponseDto.OwnedGifticonDto.builder()
                .id(entity.getId())
                .gifticon_id(entity.getId())
                .gifticonName(entity.getGifticon().getName())
                .gifticonPrice(entity.getGifticon().getPrice())
                .imageUrl(entity.getGifticon().getImageUrl())
                .usedAt(entity.getUsedAt())
                .build();
    }

    public List<UserGiftResponseDto.OwnedGifticonDto> toDto(List<UserGifticon> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    public UserGiftResponseDto.OwnedGifticonListDto toListDto(List<UserGifticon> entities) {
        return UserGiftResponseDto.OwnedGifticonListDto.builder()
                .ownedGifticonList(toDto(entities))
                .build();
    }
}
