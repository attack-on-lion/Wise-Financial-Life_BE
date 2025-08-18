package com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userCharacter;

import lombok.*;

import java.util.List;

public class UserCharResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OwnedCharacterDto {
        private Long characterId;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OwnedCharacterListDto {
        private List<OwnedCharacterDto> ownedCharacters;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PurchaseCharacterDto {
        private Long id;
    }
}
