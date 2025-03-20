package com.giunne.questservice.domain.quest.application.dto.request;

import com.giunne.commonservice.domain.common.Pageable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로드맵별 퀘스트 종류 조회 요청DTO")
public class GetQuestTypeSearchRequestDto extends Pageable {
        @Schema(
                description = "로드맵 번호",
                example = "1"
        )
        Long roadmapId;
}


