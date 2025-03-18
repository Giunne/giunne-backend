package com.giunne.questservice.domain.questPost.domain.comment.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 퀘스트 게시물 댓글 내용
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestPostCommentContent {
    @Column(name = "quest_post_comment_content", nullable = false)
    private String value;

    private QuestPostCommentContent(final String value) {
        this.value = value;
    }

    public static QuestPostCommentContent from(final String value) {
        return new QuestPostCommentContent(value);
    }
}
