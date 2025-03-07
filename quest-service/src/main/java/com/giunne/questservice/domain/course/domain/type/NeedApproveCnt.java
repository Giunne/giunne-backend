package com.giunne.questservice.domain.course.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NeedApproveCnt {
    @Column(name = "need_approve_cnt", nullable = false)
    private Integer value;

    private NeedApproveCnt(final Integer value) {
        this.value = value;
    }

    public static NeedApproveCnt from(final Integer value) {
        return new NeedApproveCnt(value);
    }
    
}
