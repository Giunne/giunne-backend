package com.giunne.questservice.domain.questPost.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 퀘스트 게시물 내용
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestPostContent {
    @Column(name = "quest_post_content", nullable = false)
    private String value;

    private QuestPostContent(final String value) {
        this.value = value;
    }

    public static QuestPostContent from(final String value) {
        return new QuestPostContent(value);
    }
}
