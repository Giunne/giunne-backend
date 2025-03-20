package com.giunne.commonservice.infra.external.domain.quest.client.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "플레이어 정보 수정 요청DTO")
public record UpdatePlayerRequestDto(
        Long avatarId,
        String avatarNickname,
        Long memberId,
        String userName,
        String nickname
) {
}