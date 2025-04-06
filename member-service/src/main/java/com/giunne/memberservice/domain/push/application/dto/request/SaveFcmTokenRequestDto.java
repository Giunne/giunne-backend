package com.giunne.memberservice.domain.push.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 FCM토큰 저장 요청DTO")
public record SaveFcmTokenRequestDto(
        @Schema(
                description = "토큰",
                example = "abcdefghijklmnopqrstuvwxyz1234567890"
        )
        String token
) {
}
