package com.giunne.questservice.domain.player.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Player {
    private Long id;
    private Long memberId;
    private String userName;
    private String nickname;
    private Long avatarId;
    private String avatarNickname;
}
