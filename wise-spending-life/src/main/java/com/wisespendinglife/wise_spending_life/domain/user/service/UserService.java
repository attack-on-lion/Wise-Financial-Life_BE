package com.wisespendinglife.wise_spending_life.domain.user.service;

import com.wisespendinglife.wise_spending_life.domain.user.dto.UserRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.user.dto.UserResponseDTO;

public interface UserService {
    //유저 조회
    UserResponseDTO getUserInfo(Long userId);

    //유저 수정 기능
    void updateUserInfo(Long userId, UserRequestDTO userRequestDTO);

    Long createUser(UserRequestDTO dto);

}
