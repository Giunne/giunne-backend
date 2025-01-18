package com.giunne.common.jwt.application.usecase

data class TokenDto(
    val token: String,
    val expiresIn: Long
)