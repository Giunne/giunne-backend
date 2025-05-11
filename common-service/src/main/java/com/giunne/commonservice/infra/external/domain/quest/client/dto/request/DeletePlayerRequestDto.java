package com.giunne.commonservice.infra.external.domain.quest.client.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "플레이어 정보 삭제 요청DTO")
public record DeletePlayerRequestDto(
        Long avatarId
) {
}