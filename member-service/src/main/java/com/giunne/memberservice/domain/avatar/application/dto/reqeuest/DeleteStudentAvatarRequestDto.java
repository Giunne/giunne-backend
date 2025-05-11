package com.giunne.memberservice.domain.avatar.application.dto.reqeuest;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "학생 아바타 삭제 요청DTO")
public record DeleteStudentAvatarRequestDto(
        @Schema(
                description = "아바타 번호",
                example = "1"
        )
        Long avatarId
) {
}
