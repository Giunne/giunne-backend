package com.giunne.questservice.domain.questPost.repository.jpa;

import com.giunne.questservice.domain.questPost.domain.like.type.LikeId;
import com.giunne.questservice.domain.questPost.repository.entity.QuestPostCommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JpaQuestPostCommentLikeRepository extends JpaRepository<QuestPostCommentLikeEntity, LikeId> {
    List<QuestPostCommentLikeEntity> findById_TargetId(Long targetId);
}
