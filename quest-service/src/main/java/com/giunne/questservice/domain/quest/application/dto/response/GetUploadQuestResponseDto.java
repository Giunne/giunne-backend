package com.giunne.questservice.domain.quest.application.dto.response;

import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyRecreationAvatarResponseDto;
import com.giunne.questservice.domain.quest.domain.type.CooperationType;
import com.giunne.questservice.domain.quest.domain.type.QuestType;
import com.giunne.questservice.domain.quest.domain.type.TrainingType;
import com.giunne.questservice.domain.questPost.domain.post.type.QuestPostProgressType;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "퀘스트 게시물 응답DTO")
public class GetUploadQuestResponseDto {
    @Schema(
            description = "퀘스트 번호",
            example = "1"
    )
    Long id;
    @Schema(
            description = "퀘스트명",
            example = "코어 1-1"
    )
    String questName;
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
            description = "필요 인증 횟수",
            example = "2"
    )
    Integer needApproveCount;
    @Schema(
            description = "현재 인증 횟수",
            example = "1"
    )
    Integer currentApproveCount;

    @Schema(
            description = "퀘스트 진행 타입"
    )
    QuestProgress questProgress;
    @Schema(
            description = "게시물 ID",
            example = "1"
    )
    Long questPostId;
    @Schema(
            description = "게시물 상태"
    )
    QuestPostProgressType questPostProgressType;
    @Schema(
            description = "인증 생성날짜"
    )
    LocalDateTime createTime;
    @Schema(
            description = "인증 수정날짜"
    )
    LocalDateTime updateTime;
    @Schema(
            description = "댓글 수",
            example = "3",
            defaultValue = "0"
    )
    @Builder.Default
    Long commentCount = 0L;
    @Schema(
            hidden = true
    )
    Long avatarId;
    @Schema(
            description = "닉네임",
            example = "아한 어린이"
    )
    String nickname;

    @Schema(
            description = "플레이어 프로필 정보"
    )
    GetMyRecreationAvatarResponseDto playerInfo;

}