package com.wisespendinglife.wise_spending_life.domain.user.converter;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.user.dto.UserResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.user.dto.UserRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
public class UserConverter {

    //Entity -> ResponseDTO //전체 유저 정보 전달
    public static UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .compositeId(user.getComposite().getId())
                .name(user.getName())
                .age(user.getAge())
                .profileImgUrl(user.getProfileImgUrl())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .baseAmount(user.getBaseAmount())
                .gender(user.getGender())
                .location(user.getLocation())
                .categoryName(user.getCategory().getName())
                .point(user.getPoint())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    // toEntity
    public static User toEntity(UserRequestDTO dto, Category category) {
        return User.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .profileImgUrl(dto.getProfileImgUrl())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .baseAmount(dto.getBaseAmount())
                .gender(dto.getGender())
                .location(dto.getLocation())
                .motivationMessage(dto.getMotivationMessage())
                .category(category)
                .point(0L)  // 첫 유저 0 포인트
                .isDeleted(false)
                .build();
    }
}
