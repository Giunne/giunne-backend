package com.giunne.questservice.domain.quest.api.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Clear {

    @Embedded
    private IsClear isClear = IsClear.from(false); // 클리어 유무

    @Embedded
    private ClearDate clearDater; // 클리어 날짜

    @Builder
    public Clear(IsClear isClear, ClearDate clearDater) {
        this.isClear = isClear;
        this.clearDater = clearDater;
    }

}
