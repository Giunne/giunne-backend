package com.giunne.questservice.domain.questPost.application.dto.response;

import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyRecreationAvatarResponseDto;
import com.giunne.questservice.domain.quest.domain.type.QuestType;
import com.giunne.questservice.domain.questPost.domain.post.type.QuestPostProgressType;
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
@Schema(description = "퀘스트 게시물 응답DTO")
public class GetPostResponseDto {
    @Schema(
            description = "퀘스트 ID",
            example = "1"
    )
    Long id;
    @Schema(
            description = "게시물 제목",
            example = "게시물 제목",
            hidden = true
    )
    String title;
    @Schema(
            description = "게시물 내용",
            example = "게시물 내용",
            hidden = true
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
            description = "플레이어 번호"
    )
    Long playerId;
    @Schema(
            description = "퀘스트 ID"
    )
    Long questId;
    @Schema(
            description = "플레이어 프로필 정보"
    )
    GetMyRecreationAvatarResponseDto playerInfo;
    @Schema(
            description = "퀘스트 정보"
    )
    QuestInfoResponseDto questInfo;
    @Schema(
            description = "생성날짜"
    )
    LocalDateTime createTime;
    @Schema(
            description = "수정날짜"
    )
    LocalDateTime updateTime;
}
