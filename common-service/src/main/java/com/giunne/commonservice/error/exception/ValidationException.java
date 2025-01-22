package com.giunne.commonservice.error.exception;

import com.giunne.commonservice.domain.common.EnumMapperType;

public class ValidationException extends BusinessException{

    public ValidationException(EnumMapperType errorCode) {
        super(errorCode);
    }
}
