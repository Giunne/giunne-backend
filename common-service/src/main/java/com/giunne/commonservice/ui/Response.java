package com.giunne.commonservice.ui;

import com.giunne.commonservice.domain.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

/**
 * 공통 Response 객체
 */

public record Response<T>(
        @Schema(description = "에러 코드"
                , example = "200")
        int code,
        @Schema(description = "에러 메시지"
                , example = "성공")
        String message,
        T value
) {

    public static <T> Response<T> ok(T value) {
        return new Response<>(HttpStatus.OK.value(), "OK", value);
    }

    public static <T> Response<T> error(ErrorCode code) {
        return new Response<>(code.getHttpStatus().value(), code.getMessage(), null);
    }
}

