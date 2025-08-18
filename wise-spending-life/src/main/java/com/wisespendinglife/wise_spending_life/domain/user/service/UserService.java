package com.wisespendinglife.wise_spending_life.domain.user.service;

import com.wisespendinglife.wise_spending_life.domain.user.dto.UserRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.user.dto.UserResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;

public interface UserService {
    //유저 조회
    UserResponseDTO getUserInfo(Long userId);

    //유저 수정 기능
    void updateUserInfo(Long userId, UserRequestDTO userRequestDTO);

    void updateComposite(Long userId, Long CompositeId);

    Long createUser(UserRequestDTO dto);

    User getEntity(Long userId);

}
