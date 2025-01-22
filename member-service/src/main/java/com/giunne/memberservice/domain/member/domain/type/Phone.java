package com.giunne.memberservice.domain.member.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 휴대폰 번호
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Phone {

    @Column(name = "phone_number")
    private String value;

    private Phone(final String value) {
        this.value = value;
    }

    public static Phone from(final String value) {
        return new Phone(value);
    }

}
