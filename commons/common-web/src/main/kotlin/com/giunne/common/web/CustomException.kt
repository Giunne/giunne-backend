package com.giunne.common.web

import org.springframework.http.HttpStatus

open class CustomException(
    val httpStatus: HttpStatus,
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message) {}
