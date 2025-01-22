package com.giunne.memberservice.domain.member.domain.type;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 ID
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginId {
    private String loginId;

    private LoginId(final String value) {
        this.loginId = value;
    }

    public static LoginId from(final String value) {
        return new LoginId(value);
    }
}
