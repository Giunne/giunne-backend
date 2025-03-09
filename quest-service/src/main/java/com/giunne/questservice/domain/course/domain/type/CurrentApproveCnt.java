package com.giunne.questservice.domain.course.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurrentApproveCnt {
    @Column(name = "current_approve_cnt", nullable = false)
    private Integer value;

    private CurrentApproveCnt(final Integer value) {
        this.value = value;
    }

    public static CurrentApproveCnt from(final Integer value) {
        return new CurrentApproveCnt(value);
    }
    
}
