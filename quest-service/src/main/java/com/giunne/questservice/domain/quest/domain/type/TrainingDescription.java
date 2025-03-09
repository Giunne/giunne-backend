package com.giunne.questservice.domain.quest.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 설명
 */

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrainingDescription {
    @Column(name = "training_description", nullable = false)
    private String value;

    private TrainingDescription(final String value) {
        this.value = value;
    }

    public static TrainingDescription from(final String value) {
        return new TrainingDescription(value);
    }

    private void setValue(String value) {this.value = value;}
}
