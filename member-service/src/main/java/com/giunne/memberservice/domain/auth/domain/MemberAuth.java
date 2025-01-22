package com.giunne.memberservice.domain.auth.domain;

import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.memberservice.domain.member.domain.type.LoginId;
import com.giunne.memberservice.domain.member.domain.type.Password;
import lombok.Builder;
import lombok.Getter;


@Getter
public class MemberAuth {

    private final LoginId loginId;
    private final Password password;
    private final MemberRole role;
    private final Long memberId;

    @Builder
    public MemberAuth(Long memberId, String loginId, String password, MemberRole role) {
        this.memberId = memberId;
        this.loginId = LoginId.from(loginId);
        this.password = Password.createPassword(password);
        this.role = role;
    }

    public boolean matchPassword(String password) {
        return this.password.matchPassword(password);
    }

    public String getLoginId() {
        return loginId.getLoginId();
    }

    public String getPassword() {
        return password.getPassword();
    }

}
