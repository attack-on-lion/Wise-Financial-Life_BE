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
    private String name;
    private Long age;
    private String profileImgUrl;
    private String location;
    private String gender;
    private String email;
    private String phoneNumber;
    private Long point;
    private Long baseAmount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
