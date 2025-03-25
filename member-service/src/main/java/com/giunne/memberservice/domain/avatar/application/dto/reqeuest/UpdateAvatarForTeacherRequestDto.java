package com.giunne.memberservice.domain.avatar.application.dto.reqeuest;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "아바타 정보 수정 요청DTO(선생님용)")
public record UpdateAvatarForTeacherRequestDto(
        @Schema(
                description = "플레이어 번호",
                example = "1"
        )
        Long playerId,
        @Schema(
                description = "아바타 닉네임",
                example = "아하!어린이!"
        )
        String nickName,
        @Schema(
                description = "학년",
                example = "1"
        )
        Integer grade,
        @Schema(
                description = "반",
                example = "1"
        )
        Integer classNumber,
        @Schema(
                description = "번호",
                example = "10",
                nullable = true
        )
        Integer studentNumber
) {

}
