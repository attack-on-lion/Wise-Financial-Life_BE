package com.wisespendinglife.wise_spending_life.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisespendinglife.wise_spending_life.domain.user.converter.UserConverter;
import com.wisespendinglife.wise_spending_life.domain.user.dto.UserResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.user.dto.UserRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
import com.wisespendinglife.wise_spending_life.domain.user.repository.UserRepository;
//예외처리
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;


@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    //유저 정보 조회
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserInfo(Long userId){
        UserEntity user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return UserConverter.toResponseDTO(user);
    }

    //유저 정보 수정
    @Override
    @Transactional
    public void updateUserInfo(Long userId, UserRequestDTO dto){
        UserEntity user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (dto.getName() != null) user.updateName(dto.getName());
        if (dto.getProfileImgUrl() != null) user.updateProfileImgUrl(dto.getProfileImgUrl());
        if (dto.getGender() != null) user.updateGender(dto.getGender());
        if (dto.getLocation() != null) user.updateLocation(dto.getLocation());
        if (dto.getEmail() != null) user.updateEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) user.updatePhoneNumber(dto.getPhoneNumber());
        if (dto.getAge() != null) user.updateAge(dto.getAge());
        if (dto.getBaseAmount() != null) user.updateBaseAmount(dto.getBaseAmount());
    }

}
