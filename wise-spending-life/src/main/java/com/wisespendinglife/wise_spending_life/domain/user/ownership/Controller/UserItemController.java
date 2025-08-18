package com.wisespendinglife.wise_spending_life.domain.user.ownership.Controller;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userCharacter.UserCharResponseDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userItem.UserItemRequestDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userItem.UserItemResponseDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.service.UserItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserItemController {
    private final UserItemService userItemService;

    /**
     * 유저가 보유중인 아이템 리스트 반환
     * @param userId
     * @return
     */
    @GetMapping("/api/users/{user_id}/items")
    ResponseEntity<UserItemResponseDto.OwnedItemListDto> getOwnedCharacters(
            @PathVariable("user_id") Long userId
    ){
        return ResponseEntity.ok(userItemService.getOwnedItems(userId));
    }

    /**
     * 특정 유저의 아이템 구매
     * @param userId
     * @param itemId
     * @param requestDto
     * @return
     */
    @PostMapping("/user/{user_is}/items/{item_id}/purchase")
    ResponseEntity<UserItemResponseDto.PurchaseItemDto> purchaseItem(
            @PathVariable("user_id") Long userId,
            @PathVariable("item_id") Long itemId,
            @RequestBody @Validated UserItemRequestDto.PurchaseItemDto requestDto
            ){

        return ResponseEntity.ok(userItemService.addUserItem(requestDto, userId, itemId));
    }
}
