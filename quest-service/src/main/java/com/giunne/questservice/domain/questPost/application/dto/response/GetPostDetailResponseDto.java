package com.giunne.questservice.domain.questPost.application.dto.response;

import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyRecreationAvatarResponseDto;
import com.giunne.questservice.domain.quest.domain.type.QuestType;
import com.giunne.questservice.domain.questPost.domain.post.type.QuestPostProgressType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "퀘스트 게시물 응답DTO")
public class GetPostDetailResponseDto {
    @Schema(
            description = "퀘스트 ID",
            example = "1"
    )
    Long id;
    @Schema(
            description = "게시물 제목",
            example = "게시물 제목"
    )
    String title;
    @Schema(
            description = "게시물 내용",
            example = "게시물 내용"
    )
    String content;
    @Schema(
            description = "게시물 상태"
    )
    QuestPostProgressType questPostProgressType;
    @Schema(
            description = "파일URL"
    )
    String fileUrl;
    @Schema(
            description = "퀘스트 타입"
    )
    QuestType questType;
}
