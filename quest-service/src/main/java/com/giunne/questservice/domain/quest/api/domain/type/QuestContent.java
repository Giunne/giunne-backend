package com.giunne.questservice.domain.quest.api.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 퀘스트 내용
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestContent {
    @Column(name = "quest_content", nullable = false)
    private String value;

    private QuestContent(final String value) {
        this.value = value;
    }

    public static QuestContent from(final String value) {
        return new QuestContent(value);
    }
}
