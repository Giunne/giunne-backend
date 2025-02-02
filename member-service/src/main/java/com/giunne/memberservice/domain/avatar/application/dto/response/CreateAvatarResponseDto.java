package com.giunne.memberservice.domain.avatar.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CreateAvatarResponseDto (
        @Schema(
                description = "아바타 번호",
                example = "1"
        )
        Long id,
        @Schema(
                description = "닉네임",
                example = "기능성 왕"
        )
        String nickname,
        @Schema(
                description = "레크레이션 id",
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
        Long characterNo
) {
}