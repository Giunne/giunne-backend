package com.giunne.commonservice.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode{

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "invalid input value"),
    NOT_FOUND(HttpStatus.BAD_REQUEST, "not found value"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "unexpected error"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
