package com.giunne.questservice.domain.questPost.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 퀘스트 게시물 제목
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestPostTitle {
    @Column(name = "quest_post_title", nullable = false)
    private String value;

    private QuestPostTitle(final String value) {
        this.value = value;
    }

    public static QuestPostTitle from(final String value) {
        return new QuestPostTitle(value);
    }
}
