package com.giunne.memberservice.domain.member.domain.type;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 닉네임
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {
    private String nickname;

    private Nickname(final String value) {
        this.nickname = value;
    }

    public static Nickname from(final String value) {
        return new Nickname(value);
    }
}