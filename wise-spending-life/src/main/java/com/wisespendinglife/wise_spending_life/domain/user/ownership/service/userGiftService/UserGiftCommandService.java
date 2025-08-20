package com.wisespendinglife.wise_spending_life.domain.user.ownership.service.userGiftService;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userGifticon.UserGiftResponseDto;

public interface UserGiftCommandService {
    UserGiftResponseDto.PurchaseResponseDto purchaseGifticon(Long gifticonId, Long userId);
    UserGiftResponseDto.UseResponseDto useGifticon(Long gifticonId, Long userId);
}
