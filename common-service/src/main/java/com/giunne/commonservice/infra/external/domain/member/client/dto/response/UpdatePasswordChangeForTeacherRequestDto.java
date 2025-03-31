package com.giunne.commonservice.infra.external.domain.member.client.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "비밀번호 변경 요청DTO")
public record UpdatePasswordChangeForTeacherRequestDto(
        @Schema(
                description = "아바타 번호",
                example = "1"
        )
        Long avatarId,
        @Schema(
                description = "로그인 패스워드",
                example = "123***"
        )
        String password
) {
}
