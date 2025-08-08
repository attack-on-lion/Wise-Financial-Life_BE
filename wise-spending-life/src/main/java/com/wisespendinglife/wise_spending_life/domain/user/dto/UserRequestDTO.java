package com.wisespendinglife.wise_spending_life.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserRequestDTO {

        @NotBlank(message = "{INVALID_PROFILE_IMAGE}")
        @Schema(description = "사용자 프로필 이미지 URL", example = "https://example.com/profile.jpg")
        private String profileImgUrl;

        @NotBlank(message = "{INVALID_LOCATION}")
        @Schema(description = "사용자 현재 주소", example = "서울특별시 강남구")
        private String location;

        @NotBlank(message = "{INVALID_EMAIL}")
        @Email(message = "{INVALID_EMAIL_FORMAT}")
        @Schema(description = "사용자 이메일", example = "holeemolee@naver.com")
        private String email;

        @NotBlank(message = "{INVALID_PHONE_NUMBER}")
        @Schema(description = "사용자 전화번호", example = "010-1234-5678")
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "{INVALID_PHONE_NUMBER}") //무조건 010-4자리-4자리 형식만 가능
        private String phoneNumber;

        @NotBlank(message = "{INVALID_NAME}")
        @Schema(description = "사용자 이름", example = "홍길동")
        private String name;

        @NotBlank(message = "{INVALID_GENDER}")
        @Pattern(
                regexp = "^(남|여|남자|여자|남성|여성)$", //남,여,남자,여자,남성,여성만 가능
                message = "{INVALID_GENDER}"
        )
        @Schema(description = "사용자 성별", example = "남성")
        private String gender;

        @NotNull(message = "{INVALID_AGE}")
        @Min(value = 0, message = "{INVALID_AGE}")
        @Max(value = 150, message = "{INVALID_AGE}")
        @Schema(description = "사용자 나이", example = "25")
        private Long age;

        @NotNull(message = "{INVALID_BASE_AMOUNT}")
        @Min(value = 0, message = "{INVALID_BASE_AMOUNT}")
        @Schema(description = "기준 금액", example = "10000")
        private Long baseAmount;
}
