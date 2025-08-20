package com.wisespendinglife.wise_spending_life.domain.user.ownership.service.userGiftService;

import com.wisespendinglife.wise_spending_life.domain.gifticon.entity.GifticonEntity;
import com.wisespendinglife.wise_spending_life.domain.gifticon.service.GifticonService;
import com.wisespendinglife.wise_spending_life.domain.point.converter.PointConverter;
import com.wisespendinglife.wise_spending_life.domain.point.dto.PointRequestDto;
import com.wisespendinglife.wise_spending_life.domain.point.dto.PointResponseDto;
import com.wisespendinglife.wise_spending_life.domain.point.entity.SourceKind;
import com.wisespendinglife.wise_spending_life.domain.point.service.PointService;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.converter.UserGiftConverter;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userGifticon.UserGiftResponseDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.entity.UserGifticon;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.repository.UserGifticonRepository;
import com.wisespendinglife.wise_spending_life.domain.user.service.UserReadService;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserGiftCommandServiceImpl implements UserGiftCommandService {

    private final UserGifticonRepository userGifticonRepository;
    private final UserReadService userReadService;
    private final GifticonService gifticonService;
    private final UserGiftConverter userGiftConverter;
    private final PointService pointService;
    private final PointConverter pointConverter;

    @Override
    public UserGiftResponseDto.PurchaseResponseDto purchaseGifticon(Long gifticonId, Long userId) {

        User user = userReadService.getEntity(userId);
        GifticonEntity gifticon = gifticonService.getEntity(gifticonId);

        PointRequestDto.SpendPointRequestDto spendPointRequestDto =
                pointConverter.toSpendPointRequestDto(-gifticon.getPrice(), SourceKind.purchase);

        PointResponseDto.PointBalanceResponseDto balance = pointService.handlePointChange(user.getId(), spendPointRequestDto);

        UserGifticon entity = userGiftConverter.toEntity(user, gifticon);

        UserGifticon save = userGifticonRepository.save(entity);

        boolean result = save.getUser().equals(user);

        return userGiftConverter.toPurchaseResponseDto(result, balance.getBalance());
    }

    @Override
    public UserGiftResponseDto.UseResponseDto useGifticon(Long gifticonId, Long userId) {

        User user = userReadService.getEntity(userId);
        GifticonEntity gifticon = gifticonService.getEntity(gifticonId);

        UserGifticon userGifticon = userGifticonRepository.findByUserIdAndGifticonId(userId, gifticonId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GIFTICON_NOT_FOUND));

        userGifticon.updateUsedAt(LocalDateTime.now());

        return userGiftConverter.toUseResponseDto(true);
    }
}
