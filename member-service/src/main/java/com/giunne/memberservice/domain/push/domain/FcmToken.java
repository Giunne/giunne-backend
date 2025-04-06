package com.giunne.memberservice.domain.push.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class FcmToken {
    private Long id;
    private Long memberId;
    private String token;
}
