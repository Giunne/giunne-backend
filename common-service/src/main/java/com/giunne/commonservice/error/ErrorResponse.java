package com.giunne.commonservice.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private String code;
    private String message;

    public static ErrorResponse of(String errorCode, String errorMessage) {
        return ErrorResponse.builder()
                .code(errorCode)
                .message(errorMessage)
                .build();
    }

    public static ErrorResponse of(int errorCode, String errorMessage) {
        return ErrorResponse.builder()
                .code(Integer.toString(errorCode))
                .message(errorMessage)
                .build();
    }

    public static ErrorResponse of(int errorCode, BindingResult bindingResult) {
        return ErrorResponse.builder()
                .code(Integer.toString(errorCode))
                .message(createErrorMessage(bindingResult))
                .build();
    }

    public static ErrorResponse of(String errorCode, BindingResult bindingResult) {
        return ErrorResponse.builder()
                .code(errorCode)
                .message(createErrorMessage(bindingResult))
                .build();
    }

    private static String createErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }
            sb.append("[");
            sb.append(fieldError.getField());
            sb.append("] ");
            sb.append(fieldError.getDefaultMessage());
        }

        return sb.toString();
    }
}
