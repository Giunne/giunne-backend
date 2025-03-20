package com.giunne.questservice.domain.quest.application.dto.response;

import com.giunne.commonservice.domain.item.ItemGrade;
import com.giunne.questservice.domain.quest.domain.type.CooperationType;
import com.giunne.questservice.domain.quest.domain.type.QuestType;
import com.giunne.questservice.domain.quest.domain.type.TrainingType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로드맵별 퀘스트 종류 조회 응답DTO")
public class GetQuestSearchResponseDto{
        @Schema(description = "아이템 ID",
                example = "1000")
        private Long id;
        @Schema(description = "퀘스트명" ,
                example = "러닝 1주차")
        private String questName;
        @Schema(
                description = "퀘스트 타입"
        )
        QuestType questType;
        @Schema(
                description = "트레이닝 타입"
        )
        TrainingType trainingType;
        @Schema(
                description = "협력 타입"
        )
        CooperationType cooperationType;
}

