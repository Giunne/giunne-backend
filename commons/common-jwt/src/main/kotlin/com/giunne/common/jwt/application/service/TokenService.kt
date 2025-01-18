package com.giunne.common.jwt.application.service

import com.giunne.common.jwt.application.usecase.AuthenticationException
import com.giunne.common.jwt.application.usecase.TokenDto
import com.giunne.common.jwt.application.usecase.TokenUseCase
import com.giunne.common.web.ErrorCode
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class TokenService : TokenUseCase {

    @Value("\${jwt.access-token-key}")
    private lateinit var accessTokenKey: String

    @Value("\${jwt.refresh-token-key}")
    private lateinit var refreshTokenKey: String

    private lateinit var accessTokenSigningKey: SecretKey // 액세스 토큰 서명 키
    private lateinit var refreshTokenSigningKey: SecretKey // 리프레시 토큰 서명 키

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @PostConstruct
    private fun init() {
        // 액세스 토큰 서명 키 생성
        this.accessTokenSigningKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenKey))
        // 리프레시 토큰 서명 키 생성
        this.refreshTokenSigningKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshTokenKey))
    }


    override fun getTokenSubject(token: String, tokenType: TokenType) =
        getClaims(token, tokenType).subject

    override fun createToken(memberId: Long, tokenType: TokenType): TokenDto {
        val time = System.currentTimeMillis()
        val expiresIn = time + tokenType.validTime.inWholeMilliseconds

        // JWT 토큰 생성
        val token = Jwts.builder()
            .subject(memberId.toString())
            .issuedAt(Date(time))
            .expiration(Date(expiresIn))
            .signWith(this.getSigningKey(tokenType))
            .compact()

        return TokenDto(
            token = token,
            expiresIn = expiresIn,
        )
    }

    // 토큰 클레임 추출 메서드
    private fun getClaims(token: String, tokenType: TokenType) =
        try {
            // 토큰 검증 및 파싱
            Jwts.parser()
                .verifyWith(this.getSigningKey(tokenType))
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: JwtException) {
            logger.error("유효하지 않은 token", e)
            throw AuthenticationException(HttpStatus.UNAUTHORIZED,ErrorCode.NOT_VALID_TOKEN);
        }


    // 토큰 타입에 따른 서명 키 가져오는 메서드
    private fun getSigningKey(tokenType: TokenType) =
        when (tokenType) {
            TokenType.ACCESS_TOKEN -> this.accessTokenSigningKey
            TokenType.REFRESH_TOKEN -> this.refreshTokenSigningKey
        }

}