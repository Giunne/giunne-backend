package com.giunne.commonservice.error;

import com.giunne.commonservice.domain.common.EnumMapperType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode implements EnumMapperType {

    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "business exception test"),

    // 인증 && 인가
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "해당 토큰은 유효한 토큰이 아닙니다."),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "Authorization Header가 빈값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "해당 refresh token은 만료됐습니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "해당 토큰은 ACCESS TOKEN이 아닙니다."),
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN, "관리자 Role이 아닙니다."),

    // 회원
    INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "잘못된 회원 타입 입니다.(memberType : KAKAO)"),
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "이미 가입된 회원 입니다."),
    MEMBER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "해당 회원은 존재하지 않습니다."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "이메일 형식이 잘못됐습니다."),
    INVALID_OAUTHTYPE(HttpStatus.BAD_REQUEST, "로그인 타입이 잘못됐습니다."),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "회원 역할 타입이 잘못됐습니다."),
    INVALID_GENDER(HttpStatus.BAD_REQUEST, "성별 타입이 잘못됐습니다."),
    INVALID_BIRTH_FORMAT(HttpStatus.BAD_REQUEST, "생년월일 형식이 잘못됐습니다."),
    DUPLICATE_MEMBER_EXIST(HttpStatus.BAD_REQUEST, "중복된 회원가입 입니다."),
    INVALID_PASSWORD_CHECK(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    EMAIL_IS_EXISTS(HttpStatus.BAD_REQUEST, "존재하는 이메일입니다."),

    //학교
    INVALID_ATPT_OFCDC_SC_CODE_NAME(HttpStatus.BAD_REQUEST, "시도교육청코명이 잘못됐습니다."),
    INVALID_ATPT_OFCDC_SC_CODE(HttpStatus.BAD_REQUEST, "시도교육청코드 타입이 잘못됐습니다."),
    INVALID_SCHUL_KND_SC_NM(HttpStatus.BAD_REQUEST, "학교의 종류 타입이 잘못됐습니다."),

    // 프로필
    PROFILE_SIZE_LIMIT(HttpStatus.BAD_REQUEST, "제출할 수 있는 파일 사이즈를 초과했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getCode() {
        return Integer.toString(httpStatus.value());
    }

    @Override
    public String getTitle() {
        return message;
    }
}
