package com.giunne.questservice.domain.questPost.repository;

import com.giunne.questservice.domain.questPost.application.interfaces.QuestPostCommentRepository;
import com.giunne.questservice.domain.questPost.application.interfaces.QuestPostRepository;
import com.giunne.questservice.domain.questPost.domain.QuestPost;
import com.giunne.questservice.domain.questPost.domain.QuestPostComment;
import com.giunne.questservice.domain.questPost.repository.entity.QuestPostCommentEntity;
import com.giunne.questservice.domain.questPost.repository.entity.QuestPostEntity;
import com.giunne.questservice.domain.questPost.repository.jpa.JpaQuestPostCommentRepository;
import com.giunne.questservice.domain.questPost.repository.jpa.JpaQuestPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestPostCommentRepositoryImpl implements QuestPostCommentRepository {

    private final JpaQuestPostCommentRepository jpaQuestPostCommentRepository;

    @Override
    public QuestPostComment findById(Long id) {
        QuestPostCommentEntity entity = jpaQuestPostCommentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        return entity.toQuestPostComment();
    }

    @Override
    @Transactional
    public QuestPostComment save(QuestPostComment comment) {
        if (comment.getId() != null) {
            jpaQuestPostCommentRepository.updateComment(comment);
            return comment;
        }
        QuestPostCommentEntity save = jpaQuestPostCommentRepository.save(new QuestPostCommentEntity(comment));
        return save.toQuestPostComment();
    }

}
