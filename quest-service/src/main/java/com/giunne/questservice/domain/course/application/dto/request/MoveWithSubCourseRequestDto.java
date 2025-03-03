package com.giunne.questservice.domain.course.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "코스 하위 이동 요청DTO")
public record MoveWithSubCourseRequestDto (
        @Schema(
                description = "현재 코스 번호",
                example = "1"
        )
        Long currentId,
        @Schema(
                description = "하위 코스 번호",
                example = "1"
        )
        Long moveId
) {
}
