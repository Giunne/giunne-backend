package com.giunne.questservice.domain.questPost.application.dto.response;

import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyRecreationAvatarResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetQuestCommentResponseDto {
    private Long id;
    private String content;
    private Long playerId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long likeCount;
    private boolean isLikedByMe;
    private GetMyRecreationAvatarResponseDto playerInfo;
}
