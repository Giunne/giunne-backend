package com.giunne.questservice.domain.quest.api.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 순서번호
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SortSeq {

    @Column(name = "sort_seq", nullable = false)
    private Long value;

    private SortSeq(final Long value) {
        this.value = value;
    }

    public static SortSeq from(final Long value) {
        return new SortSeq(value);
    }

}