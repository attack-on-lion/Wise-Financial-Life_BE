package com.wisespendinglife.wise_spending_life.domain.item.converter;

import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterRequestDto;
import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterResponseDto;
import com.wisespendinglife.wise_spending_life.domain.character.entity.Character;
import com.wisespendinglife.wise_spending_life.domain.item.dto.ItemRequestDto;
import com.wisespendinglife.wise_spending_life.domain.item.dto.ItemResponseDto;
import com.wisespendinglife.wise_spending_life.domain.item.entity.Item;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemConverter {
    public ItemResponseDto.ItemCreateDto toCreateResponseDto(Item item) {
        return ItemResponseDto.ItemCreateDto.builder()
                .id(item.getId())
                .build();
    }

    public Item toEntity(ItemRequestDto.CreateItemDto dto) {
        return Item.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .imageUrl(dto.getImageUrl())
                .price(dto.getPrice())
                .build();
    }

    public ItemResponseDto.Item toItemDto(Item entity) {
        return ItemResponseDto.Item.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .imageUrl(entity.getImageUrl())
                .price(entity.getPrice())
                .build();
    }

    public List<ItemResponseDto.Item> toItemDtos(List<Item> entities) {
        return  entities.stream()
                .map(this::toItemDto)
                .toList();
    }

    /**
     * 최종 ResponseDto 로 변환
     * @param summary - 총 개수 포함
     * @param pageInfo - 페이지 정보 포함
     * @param items - Item 레코드 리스트
     * @return
     */
    public ItemResponseDto.Items assembleItemsDto(
            ItemResponseDto.Summary summary,
            ItemResponseDto.PageInfo pageInfo,
            List<ItemResponseDto.Item> items) {
        return ItemResponseDto.Items.builder()
                .summary(summary)
                .pageInfo(pageInfo)
                .items(items)
                .build();
    }
}
