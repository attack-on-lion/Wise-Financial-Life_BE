package com.wisespendinglife.wise_spending_life.domain.user.ownership.service;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userCharacter.UserCharResponseDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userItem.UserItemRequestDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userItem.UserItemResponseDto;

public interface UserItemService {
    UserItemResponseDto.OwnedItemListDto getOwnedItems(Long userId);
    UserItemResponseDto.PurchaseItemDto addUserItem(
            UserItemRequestDto.PurchaseItemDto requestDto,
            Long userId,
            Long itemId
            );
    Boolean isOwned(Long userId, Long compositeId);

}
