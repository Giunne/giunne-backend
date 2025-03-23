package com.giunne.questservice.domain.questPost.repository.jpa;

import com.giunne.questservice.domain.questPost.domain.QuestPost;
import com.giunne.questservice.domain.questPost.repository.entity.QuestPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaQuestPostRepository extends JpaRepository<QuestPostEntity, Long> {

    @Modifying
    @Query("UPDATE QuestPostEntity p "
            + "SET p.questPostProgressType = :#{#questPost.getQuestPostProgressType()}, "
            + "p.updateTime = now() "
            + "WHERE p.id = :#{#questPost.getId()}")
    void updatePostProgress(@Param("questPost")QuestPost questPost);

}
