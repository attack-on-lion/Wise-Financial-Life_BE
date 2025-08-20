package com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserItemRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PurchaseItemDto {
        private Long price;
    }
}
