package com.wisespendinglife.wise_spending_life.domain.item.service;

import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterRequestDto;
import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterResponseDto;
import com.wisespendinglife.wise_spending_life.domain.character.entity.Character;
import com.wisespendinglife.wise_spending_life.domain.item.dto.ItemRequestDto;
import com.wisespendinglife.wise_spending_life.domain.item.dto.ItemResponseDto;
import com.wisespendinglife.wise_spending_life.domain.item.entity.Item;

public interface ItemService {
    ItemResponseDto.Items getItems(int currenPage, int size);
    ItemResponseDto.ItemCreateDto saveItem(ItemRequestDto.CreateItemDto requestDto);
    Item getEntity(Long itemId);
}
