package com.giunne.common.web


enum class ErrorCode(val code: String, var message: String) {

    TEST("T001", "business exception test"),

    // 인증 && 인가
    TOKEN_EXPIRED("AUTH-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN("AUTH-002", "해당 토큰은 유효한 토큰이 아닙니다."),
    NOT_EXISTS_AUTHORIZATION("AUTH-003", "Authorization Header가 빈값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE("AUTH-004", "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND("AUTH-005", "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED("AUTH-006", "해당 refresh token은 만료됐습니다."),
    NOT_ACCESS_TOKEN_TYPE("AUTH-007", "해당 토큰은 ACCESS TOKEN이 아닙니다."),
    FORBIDDEN_ADMIN("AUTH-008", "관리자 Role이 아닙니다."),

    // 회원
    INVALID_MEMBER_TYPE("MEMBER-001", "잘못된 회원 타입 입니다.(memberType : KAKAO)"),
    ALREADY_REGISTERED_MEMBER("MEMBER-002", "이미 가입된 회원 입니다."),
    MEMBER_NOT_EXISTS("MEMBER-003", "해당 회원은 존재하지 않습니다."),
    INVALID_EMAIL_FORMAT("MEMBER-004", "이메일 형식이 잘못됐습니다."),
    INVALID_OAUTHTYPE("MEMBER-05", "로그인 타입이 잘못됐습니다."),
    INVALID_ROLE("MEMBER-006", "회원 역할 타입이 잘못됐습니다."),
    INVALID_GENDER("MEMBER-007", "성별 타입이 잘못됐습니다."),
    INVALID_BIRTH_FORMAT("MEMBER-008", "생년월일 형식이 잘못됐습니다."),
    DUPLICATE_MEMBER_EXIST("MEMBER-009", "중복된 회원가입 입니다."),
    INVALID_PASSWORD_CHECK("MEMBER-010", "비밀번호가 일치하지 않습니다."),
    EMAIL_IS_EXISTS("MEMBER-011", "존재하는 이메일입니다."),

    //학교
    INVALID_ATPT_OFCDC_SC_CODE_NAME("SCHOOL-001", "시도교육청코명이 잘못됐습니다."),
    INVALID_ATPT_OFCDC_SC_CODE("SCHOOL-002", "시도교육청코드 타입이 잘못됐습니다."),
    INVALID_SCHUL_KND_SC_NM("SCHOOL-003", "학교의 종류 타입이 잘못됐습니다."),

    // 프로필
    PROFILE_SIZE_LIMIT("PROFILE-001", "제출할 수 있는 파일 사이즈를 초과했습니다."),

    INVALID_INPUT("E001", "Invalid input parameter"),
    ORDER_NOT_FOUND("E002", "Order not found"),
    DUPLICATED_EMAIL("E003", "중복된 이메일입니다."),
    LOGIN_FAILED("E004", "아이디 혹은 비밀번호가 일치하지 않습니다."),
    INSUFFICIENT_STOCK("E005", "Insufficient stock for products"),
    ORDER_CREATION_FAILED("E006", "Order creation failed"),
    PASSWORD_NOT_MATCH("E007", "비밀번호가 일치하지 않습니다."),
    SHOPPING_CART_NOT_FOUND("E101", "장바구니 상품이 존재하지 않습니다."),
    REVIEW_NOT_FOUND("E201", "리뷰가 존재하지 않습니다."),
    UNAUTHORIZED("E401", "인증이 만료되었습니다."),
    PERMISSION_ERROR("E403", "Permission Denied"),
    INTERNAL_SERVER_ERROR("E999", "Internal server error"),


}