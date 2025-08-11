package com.wisespendinglife.wise_spending_life.global.error;

import com.wisespendinglife.wise_spending_life.domain.score.entity.Score;
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
    INVALID_DATE_REQUEST(HttpStatus.BAD_REQUEST, "4004", "유효하지 않은 날짜 요청입니다."),
    INVALID_PAGE_REQUEST(HttpStatus.BAD_REQUEST, "4005", "유효하지 않은 페이지 요청입니다."),
    INVALID_AMOUNT(HttpStatus.BAD_REQUEST, "4006", "유효하지 않은 금액입니다."),
    INVALID_CATEGORY_REQUEST(HttpStatus.BAD_REQUEST, "4007", "유효하지 않은 카테고리 요청입니다."),
    INVALID_PAYMENT_TYPE_REQUEST(HttpStatus.BAD_REQUEST, "4008", "유효하지 않은 결제 타입 요청입니다."),
    INVALID_USER_REQUEST(HttpStatus.BAD_REQUEST, "4009", "요청한 회원 정보 형식이 올바르지 않습니다."),
    INVALID_PROFILE_IMAGE(HttpStatus.BAD_REQUEST, "4010", "프로필 이미지 URL이 유효하지 않습니다."),
    INVALID_LOCATION(HttpStatus.BAD_REQUEST, "4011", "거주지 정보가 유효하지 않습니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "4012", "이메일 형식이 유효하지 않습니다."),
    INVALID_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "4013", "전화번호 형식이 유효하지 않습니다."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "4014", "이메일 형식이 유효하지 않습니다."),
    INVALID_NAME(HttpStatus.BAD_REQUEST, "4015", "이름은 필수 입력 항목입니다."),
    INVALID_GENDER(HttpStatus.BAD_REQUEST, "4016", "성별은 필수 입력 항목입니다."),
    INVALID_AGE(HttpStatus.BAD_REQUEST, "4017", "나이는 필수 입력 항목입니다."),
    INVALID_BASE_AMOUNT(HttpStatus.BAD_REQUEST, "4018", "기준 금액은 필수 입력 항목입니다."),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "4019", "사용자 ID는 필수 입력 항목입니다."),
    INVALID_CHALLENGE_NAME(HttpStatus.BAD_REQUEST, "4020", "챌린지 이름은 필수 입력 항목입니다."),
    INVALID_CHALLENGE_TYPE(HttpStatus.BAD_REQUEST, "4021", "챌린지 타입은 필수 입력 항목입니다."),
    INVALID_CHALLENGE_DAYS(HttpStatus.BAD_REQUEST, "4022", "챌린지 일수는 필수 입력 항목입니다."),
    INVALID_START_AT(HttpStatus.BAD_REQUEST, "4023", "시작일은 필수 입력 항목입니다."),
    INVALID_END_AT(HttpStatus.BAD_REQUEST, "4024", "종료일은 필수 입력 항목입니다."),
    INVALID_CREATED_AT(HttpStatus.BAD_REQUEST, "4025", "생성일은 필수 입력 항목입니다."),
    INVALID_CATEGORIES(HttpStatus.BAD_REQUEST, "4026", "카테고리는 최소 1개 이상 선택해야 합니다."),





    /* 404 NOT_FOUND */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "4040", "리소스를 찾을 수 없습니다."),
    PAYMENT_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "4041", "결제 내역을 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "4042", "카테고리를 찾을 수 없습니다."),
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "4043", "결제 내역을 찾을 수 없습니다."), // 단건 조회용
    PAYMENT_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "4044", "결제 타입을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "4045", "유저 정보를 찾을 수 없습니다."),
    SCORE_NOT_FOUND(HttpStatus.NOT_FOUND, "4046", "저장 되어있는 점수가 없습니다."),
    CHALLENGE_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "4047", "챌린지 타입을 찾을 수 없습니다."),
    CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, "4048", "진행 중인 챌린지를 찾을 수 없습니다."),

    /* 409 CONFLICT */
    CONFLICT(HttpStatus.CONFLICT, "4091", "요청 충돌이 발생했습니다."),

    /* 500 INTERNAL_SERVER_ERROR */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5000", "알 수 없는 오류가 발생했습니다."),
    JSON_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5001","JSON 직렬화/파싱 실패가 발생했습니다."),
    GPT_EMPTY_RESPONSE    (HttpStatus.INTERNAL_SERVER_ERROR, "5002", "ChatGPT 응답에 content 가 없습니다."),
    GPT_RESPONSE_PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5003", "ChatGPT 응답 파싱 실패");

    private final HttpStatus httpStatus;  // HTTP 상태코드
    private final String code;  // 에러 코드
    private final String message;  // 에러 메시지

}