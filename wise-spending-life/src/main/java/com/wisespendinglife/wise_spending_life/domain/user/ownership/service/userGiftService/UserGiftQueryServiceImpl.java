package com.wisespendinglife.wise_spending_life.domain.user.ownership.service.userGiftService;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.converter.UserGiftConverter;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userGifticon.UserGiftResponseDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.entity.UserGifticon;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.repository.UserGifticonRepository;
import com.wisespendinglife.wise_spending_life.domain.user.service.UserReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserGiftQueryServiceImpl implements UserGiftQueryService {

    private final UserGifticonRepository userGifticonRepository;
    private final UserReadService userReadService;
    private final UserGiftConverter userGiftConverter;

    @Override
    public UserGiftResponseDto.OwnedGifticonListDto getUserGiftList(Long userId) {

        // 유저가 존재하는지 체크
        userReadService.getEntity(userId);

        List<UserGifticon> entities = userGifticonRepository.findByUser_Id(userId);

        return userGiftConverter.toListDto(entities);
    }
}
