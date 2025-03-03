package com.giunne.questservice.domain.course.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public class CourseInfoResponseDto {

    @Schema(
            description = "코스 정보"
    )
    Map<Long, List<CourseResponseDto>> courseInfo;

}
