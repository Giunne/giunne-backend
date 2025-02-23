package com.giunne.memberservice.domain.avatar.application.dto.reqeuest;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "로그인 요청DTO")
public record LoginPlayerRequestDto(
        @Schema(
                description = "플레이어 ID",
                example = "1"
        )
        Long playerId
) {
}
