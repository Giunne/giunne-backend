package com.giunne.questservice.domain.course.application.dto.response;

import com.giunne.questservice.domain.course.domain.type.Color;
import com.giunne.questservice.domain.course.domain.type.CooperationType;
import com.giunne.questservice.domain.course.domain.type.Position;
import com.giunne.questservice.domain.course.domain.type.TrainingType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CourseResponseDto(
        @Schema(
                description = "코스 번호",
                example = "1"
        )
        Long id,
        @Schema(
                description = "코스명",
                example = "코어 1-1"
        )
        String courseName,
        @Schema(
                description = "제목",
                example = "1-1"
        )
        String title,

        @Schema(
                description = "설명",
                example = "설명"
        )
        String description,

        @Schema(
                description = "색상"
        )
        Color color,
        @Schema(
                description = "난이도",
                example = "3"
        )
        Long difficultyLevel,
        @Schema(
                description = "팀플레이 유무",
                example = "true"
        )
        Boolean isTeam,
        @Schema(
                description = "협력 구분"
        )
        CooperationType cooperationType,
        @Schema(
                description = "트레이닝 구분"
        )
        TrainingType trainingType,
        @Schema(
                description = "마감일"
        )
        LocalDateTime deadline,
        @Schema(
                description = "순서번호(낮은 값이 우선순위 높음)",
                example = "1"
        )
        Long sortSeq ,
        @Schema(
                description = "로드맵 ID)",
                example = "1"
        )
        Long roadMapId ,
        @Schema(
                description = "위치 정보",
                example = "1"
        )
        Position position,

        @Schema(
                description = "최상위(root) 여부",
                example = "true"
        )
        Boolean isRoot,
        @Schema(
                description = "말단(leaf)여부",
                example = "true"
        )
        Boolean isLeaf,
        @Schema(
                description = "아이콘 URL"
        )
        String thumbnailUrl,
        @Schema(
                description = "부모 노드 리스트"
        )
        List<Long> parent
        ) {
}
