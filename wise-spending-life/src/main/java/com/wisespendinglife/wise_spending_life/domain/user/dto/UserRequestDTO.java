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

        @NotBlank(message = "{INVALID_EMAIL}")
        @Email(message = "{INVALID_EMAIL_FORMAT}")
        private String email;

        @NotBlank(message = "{INVALID_PHONE_NUMBER}")
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "{INVALID_PHONE_NUMBER}") //무조건 010-4자리-4자리 형식만 가능
        private String phoneNumber;

        @NotBlank(message = "{INVALID_NAME}")
        private String name;

        @NotBlank(message = "{INVALID_GENDER}")
        @Pattern(
                regexp = "^(남|여|남자|여자|남성|여성)$", //남,여,남자,여자,남성,여성만 가능
                message = "{INVALID_GENDER}"
        )
        private String gender;

        @NotNull(message = "{INVALID_AGE}")
        private Long age;

        @NotNull(message = "{INVALID_BASE_AMOUNT}")
        private Long baseAmount;
}
