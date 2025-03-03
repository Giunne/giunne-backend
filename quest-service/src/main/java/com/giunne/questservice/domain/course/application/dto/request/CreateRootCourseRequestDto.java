package com.giunne.questservice.domain.course.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "코스 생성 요청DTO")
public record CreateRootCourseRequestDto(
        @Schema(
                description = "코스 명",
                example = "코어 1-1"
        )
        String courseName,
        @Schema(
                description = "로드맵 ID",
                example = "1"
        )
        Long roadMapId
) {
}