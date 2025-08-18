package com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userItem;

import lombok.*;

import java.util.List;

public class UserItemResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OwnedItemDto {
        private Long itemId;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OwnedCharacterListDto {
        private List<OwnedItemDto> ownedItems;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PurchaseItemDto {
        private Long id;
    }
}
