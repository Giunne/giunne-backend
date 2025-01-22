package com.giunne.questservice.domain.quest.api.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 퀘스트명
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestName {
    @Column(name = "quest_name", nullable = false)
    private String value;

    private QuestName(final String value) {
        this.value = value;
    }

    public static QuestName from(final String value) {
        return new QuestName(value);
    }
}
