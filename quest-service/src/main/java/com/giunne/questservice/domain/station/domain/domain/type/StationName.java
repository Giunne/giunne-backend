package com.giunne.questservice.domain.station.domain.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 코스명
 */

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationName {
    @Column(name = "name")
    private String value;

    private StationName(final String value) {
        this.value = value;
    }

    public static StationName from(final String value) {
        return new StationName(value);
    }

    private void setValue(String value) {this.value = value;}
}
