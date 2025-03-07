package com.giunne.questservice.domain.quest.application.dto.request;

import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.quest.domain.Quest;
import com.giunne.questservice.domain.quest.domain.type.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(description = "코스 생성 요청DTO")
public record CreateQuestRequestDto(
        @Schema(
                description = "퀘스트명",
                example = "퀘스트명",
                nullable = true
        )
        String questName,
        @Schema(
                description = "코스 ID",
                example = "1"
        )
        Long courseId,
        @Schema(
                description = "레크레이션 ID",
                example = "1"
        )
        Long recreationId,
        @Schema(
                description = "마김일자",
                nullable = true
        )
        LocalDateTime deadline,
        @Schema(
                description = "퀘스트 내용",
                example = "퀘스트 내용",
                nullable = true
        )
        String questContent,
        @Schema(
                description = "보상 포인트",
                example = "0",
                nullable = true
        )
        Long rewardPoint,
        @Schema(
                description = "보상 경험치",
                example = "0",
                nullable = true
        )
        Long rewardExp,
        @Schema(
                description = "가능 레벨",
                example = "1",
                nullable = true
        )
        Long needLevel,
        @Schema(
                description = "난이도",
                example = "1",
                nullable = true
        )
        Long difficultyLevel,
        @Schema(
                description = "순서번호",
                example = "1",
                nullable = true
        )
        Long sortSeq,
        @Schema(
                description = "팀전 여부",
                example = "false",
                nullable = true
        )
        boolean isTeam,
        @Schema(
                description = "최대 인원수",
                example = "1",
                nullable = true
        )
        Integer maxPlayer,
        @Schema(
                description = "최소 인원수",
                example = "1",
                nullable = true
        )
        Integer minPlayer,
        @Schema(
                description = "퀘스트 타입",
                nullable = true
        )
        String questType,
        @Schema(
                description = "협력 타입",
                nullable = true
        )
        String cooperationType,
        @Schema(
                description = "퀘스트 설명",
                example = "퀘스트 설명",
                nullable = true
        )
        String questDescription,
        @Schema(
                description = "훈련 설명",
                example = "훈련 설명",
                nullable = true
        )
        String trainingDescription
) {

        public Quest toQuest(){
                return Quest.builder()
                        .questName(QuestName.from(questName))
                        .recreationNo(recreationId)
                        .course(Course.builder()
                                .id(courseId)
                                .build())
                        .deadline(deadline)
                        .questContent(QuestContent.from(questContent))
                        .rewardPoint(RewardPoint.from(rewardPoint))
                        .rewardExp(RewardExp.from(rewardExp))
                        .needLevel(NeedLevel.from(needLevel))
                        .difficultyLevel(DifficultyLevel.from(difficultyLevel))
                        .isTeam(IsTeam.from(isTeam))
                        .maxPlayer(MaxPlayer.from(maxPlayer))
                        .minPlayer(MinPlayer.from(minPlayer))
                        .questType(QuestType.from(questType))
                        .cooperationType(CooperationType.from(cooperationType))
                        .questDescription(QuestDescription.from(questDescription))
                        .trainingDescription(TrainingDescription.from(trainingDescription))
                        .build();
        }

}