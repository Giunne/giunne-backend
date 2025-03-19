package com.giunne.questservice.domain.questPost.domain.comment.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 좋아요 수
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestPostCommentLikeCounter {

    @Column(name = "like_counter", nullable = false)
    private Long value;

    private QuestPostCommentLikeCounter(final Long value) {
        this.value = value;
    }

    public static QuestPostCommentLikeCounter from(final Long value) {
        return new QuestPostCommentLikeCounter(value);
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