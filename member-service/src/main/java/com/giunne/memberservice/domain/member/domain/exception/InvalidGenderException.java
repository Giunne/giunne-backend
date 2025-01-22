package com.giunne.memberservice.domain.member.domain.exception;

import com.giunne.commonservice.error.exception.ValidationException;

public class InvalidGenderException extends ValidationException {

    public InvalidGenderException(ErrorCode errorCode) {
        super(errorCode);
    }
}

