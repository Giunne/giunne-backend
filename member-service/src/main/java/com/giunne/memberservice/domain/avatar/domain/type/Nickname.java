package com.giunne.memberservice.domain.avatar.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 닉네임
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {
    @Column(name = "nickname", nullable = false)
    private String nickname;

    private Nickname(final String nickname) {
        this.nickname = nickname;
    }

    public static Nickname from(final String value) {
        return new Nickname(value);
    }
}