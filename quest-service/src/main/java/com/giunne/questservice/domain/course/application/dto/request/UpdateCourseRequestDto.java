package com.giunne.questservice.domain.course.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "코스 생성 요청DTO")
public record UpdateCourseRequestDto (
        @Schema(
                description = "코스명",
                example = "코어 1-1"
        )
        String courseName,
        @Schema(
                description = "현재 카테고리 번호",
                example = "1"
        )
        Long currentId
) {
}
