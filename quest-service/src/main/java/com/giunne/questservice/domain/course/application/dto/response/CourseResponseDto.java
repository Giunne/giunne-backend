package com.giunne.questservice.domain.course.application.dto.response;

import com.giunne.questservice.domain.course.domain.Course;
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
                description = "코스 설명",
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
        Long sortSeq,
        @Schema(
                description = "로드맵 ID)",
                example = "1"
        )
        Long roadMapId,
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
                description = "현재 인증 횟수"
        )
        Integer currentApproveCnt,
        @Schema(
                description = "필요 인증 횟수"
        )
        Integer needApproveCnt,
        @Schema(
                description = "보상 포인트"
        )
        Long rewardPoint,
        @Schema(
                description = "보상 경험치"
        )
        Long rewardExp,
        @Schema(
                description = "운동 설명"
        )
        String trainingDescription,
        @Schema(
                description = "가이드 URL"
        )
        String guideUrl,
        @Schema(
                description = "부모 노드 리스트"
        )
        List<Long> parent
) {
        public static CourseResponseDto from(Course course) {
                return CourseResponseDto.builder()
                        .id(course.getId())
                        .courseName(course.getCourseName().getCourseName())
                        .title(course.getTitle().getValue())
                        .description(course.getDescription().getValue())
                        .color(course.getColor())
                        .difficultyLevel(course.getDifficultyLevel().getValue())
                        .isTeam(course.getIsTeam().isValue())
                        .cooperationType(course.getCooperationType())
                        .trainingType(course.getTrainingType())
                        .deadline(course.getDeadline())
                        .sortSeq(course.getSortSeq().getValue())
                        .roadMapId(course.getRoadMap().getId())
                        .position(course.getPosition())
                        .isRoot(course.getIsRoot())
                        .isLeaf(course.getIsLeaf())
                        .thumbnailUrl(course.getThumbnailUrl() != null ? course.getThumbnailUrl().getThumbnailUrl() : null)
                        .currentApproveCnt(course.getCurrentApproveCnt().getValue())
                        .needApproveCnt(course.getNeedApproveCnt().getValue())
                        .rewardPoint(course.getRewardPoint().getValue())
                        .rewardExp(course.getRewardExp().getValue())
                        .trainingDescription(course.getTrainingDescription().getValue())
                        .guideUrl(course.getGuideUrl().getGuideUrl())
//                        .parent(course.getParent())
                        .build();
        }

}
