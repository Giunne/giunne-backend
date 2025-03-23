package com.giunne.questservice.domain.quest.application.dto.request;

import com.giunne.commonservice.domain.common.Pageable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시판 리스트 조회 요청DTO")
public class GetUploadQuestForStudentRequestDto extends Pageable {
    @Schema(
            description = "로드맵ID",
            example = "1"
    )
    Long roadMapId;
    @Schema(
            description = "퀘스트명",
            example = "코어 1-1"
    )
    String questName;
    @Schema(
            description = "닉네임",
            example = "아하 어린이"
    )
    String nickName;
}

