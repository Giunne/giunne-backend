package com.giunne.questservice.domain.quest.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "업로드한 퀘스트 조회DTO")
public record GetUploadQuestRequestDto(
        @Schema(
                description = "로드맵 번호",
                example = "1"
        )
        Long roadmapId
) {
}
