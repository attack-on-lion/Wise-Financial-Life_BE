package com.wisespendinglife.wise_spending_life.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder

public class UserResponseDTO {
    private Long id;
    private Long compositeId;
    private String name;
    private Long age;
    private String profileImgUrl;
    private String location;
    private String gender;
    private String email;
    private String phoneNumber;
    private String categoryName;
    private Long point;
    private Long baseAmount;
    
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
