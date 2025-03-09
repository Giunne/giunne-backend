package com.giunne.questservice.domain.quest.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NeedApproveCount {
    @Column(name = "need_approve_count", nullable = false)
    private Integer value;

    private NeedApproveCount(final Integer value) {
        this.value = value;
    }

    public static NeedApproveCount from(final Integer value) {
        return new NeedApproveCount(value);
    }
    
}
