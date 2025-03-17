package com.giunne.questservice.domain.quest.application.dto.response;

import com.giunne.questservice.domain.questPost.domain.type.QuestPostProgressType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "퀘스트 게시판 응답DTO")
public class UploadQuestPostInfoResponseDto {
    @Schema(
            description = "퀘스트 게시판 ID",
            example = "1"
    )
    Long id;
    @Schema(
            description = "플레이어 번호",
            example = "1"
    )
    Long playerId;
    @Schema(
            description = "퀘스트 게시판 내용",
            example = "퀘스트 게시판 내용"
    )
    String questPostContent;
    @Schema(
            description = "퀘스트 게시판 제목",
            example = "퀘스트 게시판 제목"
    )
    String questPostTitle;
    @Schema(
            description = "퀘스트 게시판 진행 타입"
    )
    QuestPostProgressType questPostProgressType;
}
