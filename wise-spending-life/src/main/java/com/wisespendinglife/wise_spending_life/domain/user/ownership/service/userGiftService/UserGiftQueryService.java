package com.wisespendinglife.wise_spending_life.domain.user.ownership.service.userGiftService;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userGifticon.UserGiftResponseDto;

public interface UserGiftQueryService {
    UserGiftResponseDto.OwnedGifticonListDto getUserGiftList(Long userId);
}
