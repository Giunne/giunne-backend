package com.giunne.commonservice.error.exception;


import com.giunne.commonservice.domain.common.EnumMapperType;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(EnumMapperType errorCode) {
        super(errorCode);
    }

}

