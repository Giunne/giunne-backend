package com.giunne.questservice.domain.questState.repository;

import com.giunne.questservice.domain.questState.domain.QuestState;
import com.giunne.questservice.domain.questState.repository.entity.QuestStateEntity;
import com.giunne.questservice.domain.questState.repository.jpa.JpaQuestStateRepository;
import com.giunne.questservice.domain.questState.application.interfaces.QuestStateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestStateRepositoryImpl implements QuestStateRepository {
    private final JpaQuestStateRepository jpaQuestStateRepository;

    @Override
    public QuestState findById(Long id) {
        QuestStateEntity questStateEntity = jpaQuestStateRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 ID입니다.")
        );
        return questStateEntity.toQuestState();
    }

    @Override
    public List<QuestState> findByPlayer(Long playerId) {
        List<QuestStateEntity> questStateEntities = jpaQuestStateRepository.findByPlayer_Id(playerId);
        return questStateEntities.stream().map(QuestStateEntity::toQuestState).toList();
    }

    @Override
    public QuestState save(QuestState questState) {
        QuestStateEntity save = jpaQuestStateRepository.save(new QuestStateEntity(questState));
        return save.toQuestState();
    }

    @Override
    @Transactional
    public List<QuestState> saveAll(List<QuestState> questStates) {
        List<QuestStateEntity> questStateEntities = questStates.stream().map(QuestStateEntity::new).toList();
        List<QuestStateEntity> stateEntities = jpaQuestStateRepository.saveAll(questStateEntities);
        return  stateEntities.stream().map(QuestStateEntity::toQuestState).toList();
    }



}
