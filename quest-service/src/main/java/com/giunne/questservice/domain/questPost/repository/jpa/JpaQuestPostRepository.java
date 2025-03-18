package com.giunne.questservice.domain.questPost.repository.jpa;

import com.giunne.questservice.domain.questPost.repository.entity.QuestPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaQuestPostRepository extends JpaRepository<QuestPostEntity, Long> {

//    @Query("SELECT p FROM QuestPostEntity p " +
//            "WHERE p.questState.quest.id = :id " +
//            "AND p.player.avatarId = :playerId ")
//    List<QuestPostEntity> findByQuestIdAndPlayerId(@Param("questId")Long questId, @Param("playerId")Long playerId);
}
