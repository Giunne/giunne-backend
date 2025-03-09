package com.giunne.commonservice.infra.external.domain.quest.client.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "퀘스트 상태 생성 요청DTO")
public record CreateQuestStateRequestDto(
        Long roadMapId,
        Long avatarId
) {
}