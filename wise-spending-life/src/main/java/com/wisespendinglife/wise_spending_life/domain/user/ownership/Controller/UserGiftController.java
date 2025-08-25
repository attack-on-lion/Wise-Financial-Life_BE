package com.wisespendinglife.wise_spending_life.domain.user.ownership.Controller;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userGifticon.UserGiftResponseDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.service.userGiftService.UserGiftCommandService;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.service.userGiftService.UserGiftQueryService;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.service.userGiftService.UserGiftQueryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserGiftController {

    private final UserGiftQueryService userGiftQueryService;
    private final UserGiftCommandService userGiftCommandService;

    @GetMapping("/users/{user_id}/gifticons")
    public ResponseEntity<UserGiftResponseDto.OwnedGifticonListDto> getOwnedGiftList(
            @PathVariable("user_id") Long userId
    ) {

        log.info(">>> [CTRL] /api/users/{}/gifticons GET", userId);

        return ResponseEntity.ok(userGiftQueryService.getUserGiftList(userId));
    }

    @PostMapping("/users/{user_id}/gifticons/{gifticon_id}/purchase")
    public ResponseEntity<UserGiftResponseDto.PurchaseResponseDto> purchaseGifticon(
        @PathVariable("user_id") Long userId,
        @PathVariable("gifticon_id") Long gifticonId
    ){
        log.info(">>> [CTRL] /api/user/{}/gifticons/{}/purchase POST", userId, gifticonId);

        return ResponseEntity.ok(userGiftCommandService.purchaseGifticon(gifticonId, userId));
    }

    @PatchMapping("/users/{user_id}/gifticons/{gifticon_id}/use")
    public ResponseEntity<UserGiftResponseDto.UseResponseDto> useGifticon(
            @PathVariable("user_id") Long userId,
            @PathVariable("gifticon_id") Long gifticonId
    ) {
        log.info(">>> [CTRL] /api/user/{}/gifticons/{}/use POST", userId, gifticonId);

        return ResponseEntity.ok(userGiftCommandService.useGifticon(userId, gifticonId));
    }

}
