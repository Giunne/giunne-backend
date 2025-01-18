package com.giunne.common.jwt.application.usecase

import com.giunne.common.jwt.application.service.TokenType

interface TokenUseCase {

    // 토큰 정보 추출 메서드
    fun getTokenSubject(token: String, tokenType: TokenType): String?
    // 토큰 생성 메서드
    fun createToken(memberId: Long, tokenType: TokenType): TokenDto
}