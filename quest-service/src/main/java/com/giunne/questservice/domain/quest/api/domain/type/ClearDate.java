package com.giunne.questservice.domain.quest.api.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClearDate {
    @Column(name = "clear_date", nullable = false)
    private LocalDateTime value;

    private ClearDate(final LocalDateTime value) {
        this.value = value;
    }

    public static ClearDate from(final LocalDateTime value) {
        return new ClearDate(value);
    }

}
