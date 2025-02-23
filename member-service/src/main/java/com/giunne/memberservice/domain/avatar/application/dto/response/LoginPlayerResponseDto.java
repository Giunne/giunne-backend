package com.giunne.memberservice.domain.avatar.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Date;

@Builder
public record LoginPlayerResponseDto(
        @Schema(
                description = "플레이어 ID",
                example = "1"
        )
        Long id,
        @Schema(
                description = "닉네임",
                example = "기능성 왕"
        )
        String nickname,
        @Schema(
                description = "레크레이션 ID",
                example = "1234"
        )
        Long recreationId,
        @Schema(
                description = "경험치",
                example = "1"
        )
        Long exp,
        @Schema(
                description = "레벨",
                example = "1"
        )
        Long level,
        @Schema(
                description = "포인트",
                example = "0"
        )
        Long point,
        @Schema(
                description = "캐릭터 번호",
                example = "1"
        )
        Long characterNo,
        @Schema(
                description = "인증타입")
        String grantType,
        @Schema(description = "access 토큰")
        String accessToken,
        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss",
                timezone = "Asia/Seoul")
        Date accessTokenExpireTime
        ) {
}