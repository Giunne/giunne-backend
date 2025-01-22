package com.giunne.memberservice.domain.member.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원명
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserName {
    private String userName;

    private UserName(final String value) {
        this.userName = value;
    }

    public static UserName from(final String value) {
        return new UserName(value);
    }
}
