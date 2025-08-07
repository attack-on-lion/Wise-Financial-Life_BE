package com.wisespendinglife.wise_spending_life.domain.user.controller;

import com.wisespendinglife.wise_spending_life.domain.user.dto.UserRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.user.dto.UserResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;

    //유저 정보 조회
    @GetMapping("/{user_id}")
    public UserResponseDTO getUserInfo(@PathVariable("user_id") Long userId) {
        return userService.getUserInfo(userId);
    }

    //유저 정보 변경(수정)
    @PatchMapping("/{user_id}")
    public ResponseEntity<Map<String, String>> updateUserInfo(
            @PathVariable("user_id") Long userId,
            @Validated @RequestBody UserRequestDTO dto
    ) {
        userService.updateUserInfo(userId, dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("msg", "회원정보가 수정되었습니다"));
    }

}
