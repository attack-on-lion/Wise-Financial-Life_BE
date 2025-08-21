package com.wisespendinglife.wise_spending_life.domain.user.ownership.service.userCharService;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userCharacter.UserCharRequestDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userCharacter.UserCharResponseDto;

public interface UserCharService {
    UserCharResponseDto.OwnedCharacterListDto getOwnedCharacters(Long userId);
    UserCharResponseDto.PurchaseCharacterDto addUserCharacter(
            UserCharRequestDto.PurchaseCharacterDto dto,
            Long userId,
            Long characterId
    );
    boolean isOwned(Long userId, Long characterId);
}
