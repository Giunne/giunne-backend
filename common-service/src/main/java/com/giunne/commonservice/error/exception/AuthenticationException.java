package com.giunne.commonservice.error.exception;


import com.giunne.commonservice.domain.common.EnumMapperType;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(EnumMapperType errorCode) {
        super(errorCode);
    }

}
