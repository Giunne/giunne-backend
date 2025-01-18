package com.giunne.common.jwt.application.usecase

import com.giunne.common.web.CustomException
import com.giunne.common.web.ErrorCode
import org.springframework.http.HttpStatus

class AuthenticationException(
    httpStatus: HttpStatus,
    errorCode: ErrorCode
) :
    CustomException(
        httpStatus,
        errorCode
    )