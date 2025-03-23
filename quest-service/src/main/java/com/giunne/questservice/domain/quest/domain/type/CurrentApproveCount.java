package com.giunne.questservice.domain.quest.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurrentApproveCount {
    @Column(name = "current_approve_count", nullable = false)
    private Integer value;

    private CurrentApproveCount(final Integer value) {
        this.value = value;
    }

    public static CurrentApproveCount from(final Integer value) {
        return new CurrentApproveCount(value);
    }

    public void increase() {
        value++;
    }

    public void decrease() {
        if (value <= 0) {
            return;
        }
        value--;
    }
}
