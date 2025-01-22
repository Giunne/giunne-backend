package com.giunne.commonservice.domain.auth.exception;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.BusinessException;

public class InvalidOAuthTypeException extends BusinessException {

    public InvalidOAuthTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
