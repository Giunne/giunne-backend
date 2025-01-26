package com.giunne.memberservice.domain.auth.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 요청DTO")
public record LoginRequestDto(
        @Schema(
                description = "로그인 ID",
                example = "test"
        )
        String loginId,
        @Schema(
                description = "로그인 패스워드",
                example = "123***"
        )
        String password
) {
}
