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
                .location(user.getLocation())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    //RequestDTO -> Entity 이미 존재하는 엔티티를 조회해서 일부 수정
    public static void updateEntity(UserEntity user, UserRequestDTO dto) {
        user.updateProfileImgUrl(dto.getProfileImgUrl());
        user.updateLocation(dto.getLocation());
        user.updateEmail(dto.getEmail());
        user.updatePhoneNumber(dto.getPhoneNumber());
    }
}
