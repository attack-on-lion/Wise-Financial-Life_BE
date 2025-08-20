package com.wisespendinglife.wise_spending_life.domain.user.ownership.converter;

import com.wisespendinglife.wise_spending_life.domain.character.entity.Character;
import com.wisespendinglife.wise_spending_life.domain.item.entity.Item;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userCharacter.UserCharResponseDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userItem.UserItemResponseDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.entity.UserCharacter;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.entity.UserItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserItemConverter {
    public UserItemResponseDto.OwnedItemDto toOwnedCharDto(UserItem entity) {
        return UserItemResponseDto.OwnedItemDto.builder()
                .itemId(entity.getId())
                .build();
    }

    /**
     * 단일 <-> 리스트 변환(now) <-> ListDto
     * 접근 단계: Private
     * @param entities
     * @return
     */
    private List<UserItemResponseDto.OwnedItemDto> toListOwnedDto(List<UserItem> entities) {
        return entities.stream()
                .map(this::toOwnedCharDto)
                .toList();
    }

    public UserItemResponseDto.OwnedItemListDto toOwnedListDto(List<UserItem> entities) {
        return UserItemResponseDto.OwnedItemListDto.builder()
                .ownedItems(toListOwnedDto(entities))
                .build();
    }


    public UserItem toEntity(User user, Item item) {
        return UserItem.builder()
                .user(user)
                .item(item)
                .build();
    }

    public UserItemResponseDto.PurchaseItemDto toPurchaseItemResponseDto(UserItem entity) {
        return UserItemResponseDto.PurchaseItemDto.builder()
                .id(entity.getId())
                .build();
    }
}
