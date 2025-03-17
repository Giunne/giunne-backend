package com.giunne.questservice.domain.quest.application.dto.response;

import com.giunne.questservice.domain.questState.domain.QuestState;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "플레이어별 퀘스트 상태 응답DTO")
public class UploadQuestStateInfoResponseDto {
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

    public UploadQuestStateInfoResponseDto(QuestState questState) {
        this.id = questState.getId();
        this.playerId = questState.getPlayer().getAvatarId();
        this.questProgress = questState.getQuestProgress();
//        this.teamId = questState.getTeam().getId();
        this.rewardPoint = questState.getRewardPoint().getValue();
        this.rewardExp = questState.getRewardExp().getValue();
        this.starPoint = questState.getStarPoint().getValue();
    }

}
