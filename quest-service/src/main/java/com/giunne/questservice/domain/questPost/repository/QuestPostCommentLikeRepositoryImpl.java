package com.giunne.questservice.domain.questPost.repository;

import com.giunne.questservice.domain.player.domain.Player;
import com.giunne.questservice.domain.questPost.application.interfaces.QuestPostCommentLikeRepository;
import com.giunne.questservice.domain.questPost.domain.QuestPostComment;
import com.giunne.questservice.domain.questPost.domain.QuestPostCommentLike;
import com.giunne.questservice.domain.questPost.repository.entity.QuestPostCommentLikeEntity;
import com.giunne.questservice.domain.questPost.repository.jpa.JpaQuestPostCommentLikeRepository;
import com.giunne.questservice.domain.questPost.repository.jpa.JpaQuestPostCommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestPostCommentLikeRepositoryImpl implements QuestPostCommentLikeRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    private final JpaQuestPostCommentLikeRepository jpaQuestPostCommentLikeRepository;
    private final JpaQuestPostCommentRepository jpaQuestPostCommentRepository;

    @Override
    public boolean checkLike(QuestPostComment comment,Player player) {
        QuestPostCommentLikeEntity entity = new QuestPostCommentLikeEntity(comment, player);
        return jpaQuestPostCommentLikeRepository.existsById(entity.getId());
    }

    @Transactional
    public void deleteByComment(QuestPostComment comment) {
        List<QuestPostCommentLikeEntity> entityList = jpaQuestPostCommentLikeRepository.findById_TargetId(comment.getId());
        if (entityList.isEmpty()) {
            return;
        }
        entityList.forEach(item -> {
            jpaQuestPostCommentLikeRepository.deleteById(item.getId());
        });
    }

    @Override
    @Transactional
    public void like(QuestPostComment comment,Player player) {
        QuestPostCommentLikeEntity entity = new QuestPostCommentLikeEntity(comment, player);
        entityManager.persist(entity);
        jpaQuestPostCommentRepository.updateLikeCount(comment);
    }

    @Override
    @Transactional
    public void unlike(QuestPostComment comment,Player player) {
        QuestPostCommentLikeEntity entity = new QuestPostCommentLikeEntity(comment, player);
        jpaQuestPostCommentLikeRepository.deleteById(entity.getId());
        jpaQuestPostCommentRepository.updateLikeCount(comment);
    }

}
