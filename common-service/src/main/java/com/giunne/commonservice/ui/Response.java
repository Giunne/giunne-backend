package com.giunne.commonservice.ui;

import com.giunne.commonservice.domain.exception.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 공통 Response 객체
 */

public record Response<T>(int code, String message, T value) {

    public static <T> Response<T> ok(T value) {
        return new Response<>(HttpStatus.OK.value(), "OK", value);
    }
    public static <T> Response<T> error(ErrorCode code) {
        return new Response<>(code.getHttpStatus().value(), code.getMessage(), null);
    }
}

