package com.wisespendinglife.wise_spending_life.domain.challenge.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ChallengeType {
    PAY_NOT("pay_not"),
    PAY_LESS("pay_less"),
    PAY_SAVE("pay_save");

    @JsonValue
    private final String value;

    @JsonCreator
    public static ChallengeType fromValue(String value) {
        return Stream.of(values())
                .filter(v -> v.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.CHALLENGE_TYPE_NOT_FOUND));
    }
}
