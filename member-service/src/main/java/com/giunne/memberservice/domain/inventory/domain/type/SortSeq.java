package com.giunne.memberservice.domain.inventory.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 정렬순서
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SortSeq {

    @Column(name = "sort_seq", nullable = false)
    private Long sortSeq;

    private SortSeq(final Long value) {
        this.sortSeq = value;
    }

    public static SortSeq from(final Long value) {
        return new SortSeq(value);
    }

}
