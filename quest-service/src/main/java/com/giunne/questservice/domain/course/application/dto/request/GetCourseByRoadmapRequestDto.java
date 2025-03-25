package com.giunne.questservice.domain.course.application.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로드맵 조회 요청DTO")
public class GetCourseByRoadmapRequestDto {
    @Parameter(
            description = "로드맵 ID",
            example = "1"
    )
    @NotNull
    private Long roadmapId;
}
