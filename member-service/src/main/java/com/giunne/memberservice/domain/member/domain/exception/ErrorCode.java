package com.giunne.memberservice.domain.member.domain.exception;

import com.giunne.commonservice.domain.common.EnumMapperType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode implements EnumMapperType {

    // 회원
    INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "잘못된 회원 타입 입니다."),
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "이미 가입된 회원 입니다."),
    MEMBER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "해당 회원은 존재하지 않습니다."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "이메일 형식이 잘못됐습니다."),
    INVALID_OAUTH_TYPE(HttpStatus.BAD_REQUEST, "로그인 타입이 잘못됐습니다."),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "회원 역할 타입이 잘못됐습니다."),
    INVALID_GENDER(HttpStatus.BAD_REQUEST, "성별 타입이 잘못됐습니다."),
    INVALID_BIRTH_FORMAT(HttpStatus.BAD_REQUEST, "생년월일 형식이 잘못됐습니다."),
    DUPLICATE_MEMBER_EXIST(HttpStatus.BAD_REQUEST, "중복된 회원가입 입니다."),
    INVALID_PASSWORD_CHECK(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    EMAIL_IS_EXISTS(HttpStatus.BAD_REQUEST, "존재하는 이메일입니다."),


    // 학교
    INVALID_LCTNSC(HttpStatus.BAD_REQUEST, "존재하지 않는 시도명입니다."),
    INVALID_FONDTYPE(HttpStatus.BAD_REQUEST, "존재하지 않는 설립형태입니다."),
    INVALID_BNHHSE(HttpStatus.BAD_REQUEST, "존재하지 않는 본교분교구분입니다."),
    INVALID_CDDC_CODE(HttpStatus.BAD_REQUEST, "존재하지 않는 시도교육청코드입니다."),
    INVALID_SCHOOL(HttpStatus.BAD_REQUEST, "유효하지 않은 학교정보입니다."),


    // File
    EMPTY_FILE(HttpStatus.BAD_REQUEST,  "빈 파일은 제출할 수 없습니다."),
    FILE_AMOUNTS_LIMIT(HttpStatus.BAD_REQUEST,  "제출할 수 있는 파일 수를 초과했습니다."),
    FILE_SIZE_LIMIT(HttpStatus.BAD_REQUEST, "제출할 수 있는 파일 사이즈를 초과했습니다."),
    FILE_FORMAT(HttpStatus.BAD_REQUEST,  "파일 형식이 잘못되었습니다."),
    INVALID_CSV_FORMAT(HttpStatus.BAD_REQUEST, "csv 파일 형식이 잘못되었습니다."),
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
