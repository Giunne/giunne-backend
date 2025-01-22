package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Embedded
    private Rdnmadr rdnmadr; // 소재지도로명주소

    @Embedded
    private Inmadr inmadr; // 소재지지번주소

    @Builder
    public Address(Rdnmadr rdnmadr, Inmadr inmadr) {
        this.rdnmadr = rdnmadr;
        this.inmadr = inmadr;
    }
}

