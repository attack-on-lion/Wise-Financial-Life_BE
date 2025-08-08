package com.wisespendinglife.wise_spending_life.domain.user.converter;

import com.wisespendinglife.wise_spending_life.domain.user.dto.UserResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.user.dto.UserRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
public class UserConverter {

    //Entity -> ResponseDTO //전체 유저 정보 전달
    public static UserResponseDTO toResponseDTO(UserEntity user) {
        return UserResponseDTO.builder()
                .name(user.getName())
                .age(user.getAge())
                .profileImgUrl(user.getProfileImgUrl())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .baseAmount(user.getBaseAmount())
                .gender(user.getGender())
                .location(user.getLocation())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    // toEntity
    public static UserEntity toEntity(UserRequestDTO dto) {
        return UserEntity.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .profileImgUrl(dto.getProfileImgUrl())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .baseAmount(dto.getBaseAmount())
                .gender(dto.getGender())
                .location(dto.getLocation())
                .isDeleted(false)
                .build();
    }
}
