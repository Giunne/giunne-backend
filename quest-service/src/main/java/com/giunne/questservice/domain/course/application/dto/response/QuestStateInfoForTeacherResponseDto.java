package com.giunne.questservice.domain.course.application.dto.response;

import com.giunne.questservice.domain.questState.domain.QuestState;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "플레이어별 퀘스트 상태 응답DTO")
public class QuestStateInfoForTeacherResponseDto {
    @Schema(
            description = "퀘스트 상태 ID",
            example = "1"
    )
    Long id;
    @Schema(
            description = "플레이어 번호",
            example = "1"
    )
    Long playerId;
    @Schema(
            description = "퀘스트 진행 타입"
    )
    QuestProgress questProgress;
    @Schema(
            description = "보상 포인트",
            example = "3"
    )
    Long rewardPoint;
    @Schema(
            description = "보상 경험치",
            example = "3"
    )
    Long rewardExp;
    @Schema(
            description = "스타 포인트",
            example = "3"
    )
    Long starPoint;
    @Schema(
            description = "추가 동작 여부",
            example = "false"
    )
    Boolean hasExtraPoints;
    @Schema(
            description = "현재 인증 횟수",
            example = "1"
    )
    Integer currentApproveCount;

    public QuestStateInfoForTeacherResponseDto(QuestState questState) {
        this.id = questState.getId();
        this.playerId = questState.getPlayer().getAvatarId();
        this.questProgress = questState.getQuestProgress();
        this.rewardPoint = questState.getRewardPoint().getValue();
        this.rewardExp = questState.getRewardExp().getValue();
        this.starPoint = questState.getStarPoint().getValue();
        this.currentApproveCount = questState.getCurrentApproveCount().getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestStateInfoForTeacherResponseDto that = (QuestStateInfoForTeacherResponseDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
