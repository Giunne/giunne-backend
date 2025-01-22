package com.giunne.commonservice.error.exception;


import com.giunne.commonservice.domain.common.EnumMapperType;
import lombok.Getter;


@Getter
public class BusinessException extends RuntimeException{

    private final EnumMapperType errorCode;

    public BusinessException(EnumMapperType errorCode) {
        super(errorCode.getTitle());
        this.errorCode = errorCode;
    }
}
