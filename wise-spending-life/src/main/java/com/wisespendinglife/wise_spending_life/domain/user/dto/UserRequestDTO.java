package com.wisespendinglife.wise_spending_life.domain.user.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserRequestDTO {

        @NotBlank(message = "{INVALID_PROFILE_IMAGE}")
        private String profileImgUrl;

        @NotBlank(message = "{INVALID_LOCATION}")
        private String location;

        // TODO: Email 양식 유효성 검사 추가
        @NotBlank(message = "{INVALID_EMAIL}")
        private String email;

        @NotBlank(message = "{INVALID_PHONE_NUMBER}")
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "{INVALID_PHONE_NUMBER}") //무조건 010-4자리-4자리 형식만 가능
        private String phoneNumber;
}
