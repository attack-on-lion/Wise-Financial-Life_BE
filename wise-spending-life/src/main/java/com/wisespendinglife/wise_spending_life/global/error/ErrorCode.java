package com.wisespendinglife.wise_spending_life.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 서비스 전역에서 사용하는 에러 코드 정의.
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "4000", "잘못된 입력 값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "4051", "허용되지 않은 HTTP Method입니다."),
    DUPLICATE_RESOURCE(HttpStatus.BAD_REQUEST, "4002", "이미 존재하는 리소스입니다."),
    INVALID_RESOURCE_STATE(HttpStatus.BAD_REQUEST, "4003", "유효하지 않은 리소스 상태입니다."),

    /* 404 NOT_FOUND */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "4040", "리소스를 찾을 수 없습니다."),
    PAYMENT_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "4041", "결제 내역을 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "4042", "카테고리를 찾을 수 없습니다."),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "4043", "결제 내역을 찾을 수 없습니다."), // 단건 조회용
    PAYMENT_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "4044", "결제 타입을 찾을 수 없습니다."),

    /* 409 CONFLICT */
    CONFLICT(HttpStatus.CONFLICT, "4091", "요청 충돌이 발생했습니다."),

    /* 500 INTERNAL_SERVER_ERROR */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5000", "알 수 없는 오류가 발생했습니다.");

    private final HttpStatus httpStatus;  // HTTP 상태코드
    private final String code;  // 에러 코드
    private final String message;  // 에러 메시지

}