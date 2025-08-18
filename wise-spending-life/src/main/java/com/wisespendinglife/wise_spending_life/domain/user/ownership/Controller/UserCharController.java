package com.wisespendinglife.wise_spending_life.domain.user.ownership.Controller;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userCharacter.UserCharRequestDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userCharacter.UserCharResponseDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.service.UserCharService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserCharController {

    private final UserCharService userCharService;

    @GetMapping("/users/{user_id}/charactors")
    public ResponseEntity<UserCharResponseDto.OwnedCharacterListDto> getOwnedCharacters(
            @PathVariable("user_id") Long userId
    ){
        return ResponseEntity.ok(userCharService.getOwnedCharacters(userId));
    }

    @PostMapping("/user/{user_is}/charactors/{character_id}/purchase")
    public ResponseEntity<UserCharResponseDto.PurchaseCharacterDto> purchase(
            @PathVariable("user_id") Long userId,
            @PathVariable("character_id") Long characterId,
            @RequestBody UserCharRequestDto.PurchaseCharacterDto requestDto
    ){
        return ResponseEntity.ok(userCharService.addUserCharacter(requestDto, userId, characterId));
    }
}
