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
public class QuestDescription {
    @Column(name = "quest_description")
    private String value;

    private QuestDescription(final String value) {
        this.value = value;
    }

    public static QuestDescription from(final String value) {
        return new QuestDescription(value);
    }

    private void setValue(String value) {this.value = value;}
}
