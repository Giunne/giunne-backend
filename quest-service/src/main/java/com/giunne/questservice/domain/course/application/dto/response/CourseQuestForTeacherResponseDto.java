package com.giunne.questservice.domain.course.application.dto.response;

import com.giunne.questservice.domain.course.domain.type.Color;
import com.giunne.questservice.domain.course.domain.type.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "코스 조회 응답DTO(선생님용)")
public class CourseQuestForTeacherResponseDto {
        @Schema(
                description = "코스 번호",
                example = "1"
        )
        Long id;
        @Schema(
                description = "코스명",
                example = "코어 1-1"
        )
        String courseName;
        @Schema(
                description = "제목",
                example = "1-1"
        )
        String title;

        @Schema(
                description = "코스 설명",
                example = "설명"
        )
        String description;

        @Schema(
                description = "색상"
        )
        Color color;
        @Schema(
                description = "순서번호(낮은 값이 우선순위 높음)",
                example = "1"
        )
        Long sortSeq;
        @Schema(
                description = "로드맵 ID)",
                example = "1"
        )
        Long roadMapId;
        @Schema(
                description = "위치 정보",
                example = "1"
        )
        Position position;
        @Schema(
                description = "최상위(root) 여부",
                example = "true"
        )
        Boolean isRoot;
        @Schema(
                description = "말단(leaf)여부",
                example = "true"
        )
        Boolean isLeaf;
        @Schema(
                description = "아이콘 URL"
        )
        String thumbnailUrl;
        @Schema(
                description = "부모 노드 리스트"
        )
        Set<Long> parent;
        @Schema(description = "퀘스트 정보")
        QuestInfoForTeacherResponseDto questInfo;

        public void changeIsLeaf(boolean isLeaf) {
                this.isLeaf = isLeaf;
        }
        public void changeIsRoot(boolean isRoot) {
                this.isRoot = isRoot;
        }

}
