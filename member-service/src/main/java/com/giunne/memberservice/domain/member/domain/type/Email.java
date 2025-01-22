package com.giunne.memberservice.domain.member.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이메일
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {


    @Column(name = "email", nullable = false, unique = true)
    private String value;

    private Email(final String value) {
        this.value = value;
    }

    public static Email from(final String value) {
        return new Email(value);
    }

}
