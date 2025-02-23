package com.giunne.commonservice.principal;

import com.giunne.commonservice.domain.auth.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberPrincipal {
    private final Long memberId;
    private final Long playerId;
    private final MemberRole role;

    @Builder
    public MemberPrincipal(Long memberId, Long playerId, String role) {
        this.memberId = memberId;
        this.playerId = playerId;
        this.role = MemberRole.valueOf(role);
    }
}

