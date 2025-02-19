package com.giunne.memberservice.domain.recreation.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecreationCode {

    @Column(name = "recreation_code", nullable = false)
    private String recreationCode;

    private RecreationCode(final String value) {
        this.recreationCode = value;
    }

    public static RecreationCode from(final String value) {
        return new RecreationCode(value);
    }

}
