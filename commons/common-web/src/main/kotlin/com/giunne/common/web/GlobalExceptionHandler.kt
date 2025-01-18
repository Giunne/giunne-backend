package com.giunne.common.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException


@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    /**
     *  요청 헤더가 누락되었을 때 발생하는 예외
     */
    @ExceptionHandler(MissingRequestHeaderException::class)
    fun handleMissingRequestHeaderException(ex: MissingRequestHeaderException): ResponseEntity<CommonResponse<Unit>> {
        logger.error("Exception", ex)
        val errorResponse = ErrorResponse("HEADER_NOT_FOUND_ERROR", "Invalid Header format")
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(CommonResponse.error(listOf(errorResponse)))
    }

    /**
     * 매개변수의 유효성 검사 실패 시 발생하는 예외
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<CommonResponse<Unit>> {
        logger.error("Exception", ex)
        val errors = ex.bindingResult.allErrors.map { error ->
            ErrorResponse("VALIDATION_ERROR", error.defaultMessage ?: "Validation error")
        }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(CommonResponse.error(errors))
    }

    /**
     *  JSON 형식화된 요청 본문의 파싱 실패 시 발생하는 예외
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseException(ex: HttpMessageNotReadableException): ResponseEntity<CommonResponse<Unit>> {
        logger.error("Exception", ex)
        val errorResponse = ErrorResponse("JSON_PARSE_ERROR", "Invalid JSON format")
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(CommonResponse.error(listOf(errorResponse)))
    }

    /**
     * 비즈니스 로직 실행 중 오류 발생
     */
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(ex: CustomException): ResponseEntity<CommonResponse<Unit>> {
        logger.error("Exception", ex)
        val errorResponse = ErrorResponse(ex.errorCode.code, ex.errorCode.message)
        return ResponseEntity
            .status(ex.httpStatus)
            .body(CommonResponse.error(listOf(errorResponse)))
    }

    /**
     * 나머지 예외 발생
     */
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<CommonResponse<Unit>> {
        logger.error("Exception", ex)
        val errorResponse = ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.code, ErrorCode.INTERNAL_SERVER_ERROR.message)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(CommonResponse.error(listOf(errorResponse)))
    }
}