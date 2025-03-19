package com.giunne.questservice.domain.questPost.application.dto.response;

import com.giunne.questservice.domain.quest.domain.Quest;
import com.giunne.questservice.domain.quest.domain.type.CooperationType;
import com.giunne.questservice.domain.quest.domain.type.QuestType;
import com.giunne.questservice.domain.quest.domain.type.TrainingType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "퀘스트 조회 응답DTO")
public class QuestInfoResponseDto {
    @Schema(
            description = "코스 번호",
            example = "1"
    )
    Long id;
    @Schema(
            description = "퀘스트명",
            example = "코어 1-1"
    )
    String questName;
    @Schema(
            description = "난이도",
            example = "3"
    )
    Long difficultyLevel;
    @Schema(
            description = "팀플레이 유무",
            example = "true"
    )
    Boolean isTeam;
    @Schema(
            description = "마감일"
    )
    LocalDateTime deadline;
    @Schema(
            description = "순서번호(낮은 값이 우선순위 높음)",
            example = "1"
    )
    Long sortSeq;
    @Schema(
            description = "필요 인증 횟수",
            example = "2"
    )
    Integer needApproveCount;
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
            description = "운동 설명",
            example = "운동 설명"
    )
    String trainingDescription;
    @Schema(
            description = "가이드 URL"
    )
    String guideUrl;

    @Schema(
            description = "가능 레벨",
            example = "1"
    )
    Long needLevel;

    @Schema(
            description = "최대 인원수",
            example = "3"
    )
    Integer maxPlayer;
    @Schema(
            description = "최소 인원수",
            example = "1"
    )
    Integer minPlayer;
    @Schema(
            description = "퀘스트 타입"
    )
    QuestType questType;
    @Schema(
            description = "협력 타입"
    )
    CooperationType cooperationType;
    @Schema(
            description = "트레이닝 타입"
    )
    TrainingType trainingType;
    @Schema(
            description = "현재 인증 횟수",
            example = "1"
    )
    Integer currentApproveCount;
    @Schema(
            description = "퀘스트 설명",
            example = "퀘스트 설명"
    )
    String questDescription;


    public QuestInfoResponseDto(Quest quest) {
        this.id = quest.getId();
        this.questName = quest.getQuestName().getValue();
        this.difficultyLevel = quest.getDifficultyLevel().getValue();
        this.isTeam = quest.getIsTeam().isValue();
        this.deadline = quest.getDeadline();
        this.sortSeq = quest.getSortSeq().getValue();
        this.needApproveCount = quest.getNeedApproveCount().getValue();
        this.rewardPoint = quest.getRewardPoint().getValue();
        this.rewardExp = quest.getRewardExp().getValue();
        this.trainingDescription = quest.getTrainingDescription().getValue();
        this.guideUrl = quest.getGuideUrl().getGuideUrl();
        this.needLevel = quest.getNeedLevel().getValue();
        this.maxPlayer = quest.getMaxPlayer().getValue();
        this.minPlayer = quest.getMinPlayer().getValue();
        this.questType = quest.getQuestType();
        this.cooperationType = quest.getCooperationType();
        this.trainingType = quest.getTrainingType();
        this.currentApproveCount = quest.getCurrentApproveCount().getValue();
        this.questDescription = quest.getQuestDescription().getValue();
    }

}
