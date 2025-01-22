package com.giunne.commonservice.principal;

import com.giunne.commonservice.domain.auth.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberPrincipal {

    private final Long memberId;
    private final MemberRole role;

    @Builder
    public MemberPrincipal(Long memberId, String role) {
        this.memberId = memberId;
        this.role = MemberRole.valueOf(role);
    }
}

