package com.giunne.questservice.domain.questPost.repository.jpa;

import com.giunne.questservice.domain.questPost.domain.QuestPostComment;
import com.giunne.questservice.domain.questPost.repository.entity.QuestPostCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaQuestPostCommentRepository extends JpaRepository<QuestPostCommentEntity, Long> {

    @Modifying
    @Query("UPDATE QuestPostCommentEntity c "
            + "SET c.likeCounter = :#{#comment.getLikeCounter()} "
            + "WHERE c.id = :#{#comment.getId()}")
    void updateLikeCount(@Param("comment")QuestPostComment comment);


    @Modifying
    @Query("UPDATE QuestPostCommentEntity c "
            + "SET c.content = :#{#comment.getContent()},"
            + "c.updateTime = now() "
            + "WHERE c.id = :#{#comment.getId()}")
    void updateComment(@Param("comment")QuestPostComment comment);

    List<QuestPostCommentEntity> findByPlayer_id(Long playerId);
}
