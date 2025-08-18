package com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userCharacter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserCharRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class PurchaseCharacterDto {
        private Long price;
    }
}
