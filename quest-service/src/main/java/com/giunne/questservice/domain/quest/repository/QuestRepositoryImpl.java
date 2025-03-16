package com.giunne.questservice.domain.quest.repository;

import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.quest.domain.QuestOpenCondition;
import com.giunne.questservice.domain.quest.repository.entity.QuestOpenConditionEntity;
import com.giunne.questservice.domain.course.repository.entity.QCourseEntity;
import com.giunne.questservice.domain.quest.repository.jpa.JpaQuestOpenConditionRepository;
import com.giunne.questservice.domain.quest.application.dto.request.UpdateQuestInfoRequestDto;
import com.giunne.questservice.domain.quest.application.interfaces.QuestRepository;
import com.giunne.questservice.domain.quest.domain.Quest;
import com.giunne.questservice.domain.quest.repository.entity.QQuestEntity;
import com.giunne.questservice.domain.quest.repository.entity.QuestEntity;
import com.giunne.questservice.domain.quest.repository.jpa.JpaQuestRepository;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import com.giunne.questservice.domain.questState.repository.entity.QQuestStateEntity;
import com.giunne.questservice.domain.questState.repository.jpa.JpaQuestStateRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestRepositoryImpl implements QuestRepository {
    private final JPAQueryFactory queryFactory;
    private final JpaQuestRepository jpaQuestRepository;
    private final QQuestEntity qQuestEntity = QQuestEntity.questEntity;
    private final QCourseEntity qCourseEntity = QCourseEntity.courseEntity;
    private final QQuestStateEntity qQuestStateEntity = QQuestStateEntity.questStateEntity;
    private final JpaQuestOpenConditionRepository jpaQuestOpenConditionRepository;
    private final JpaQuestStateRepository jpaQuestStateRepository;


    public List<Quest> findByRoadMap(Long roadMapId) {
        List<QuestEntity> fetch = queryFactory.selectFrom(qQuestEntity)
                .join(qCourseEntity).on(qQuestEntity.course.id.eq(qCourseEntity.id))
                .where(qQuestEntity.course.roadMap.id.eq(roadMapId))
                .fetch();

        return fetch.stream().map(i -> i.toQuest()).toList();
    }

    @Override
    @Transactional
    public Quest saveQuest(Quest quest) {
        QuestEntity savedQuest = jpaQuestRepository.save(new QuestEntity(quest));
        return savedQuest.toQuest();
    }

    @Override
    public Quest findById(Long id) {
        QuestEntity questEntity = jpaQuestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퀘스트입니다."));

        return questEntity.toQuest();
    }

    @Override
    @Transactional
    public void deleteQuest(Quest quest) {
        jpaQuestRepository.deleteById(quest.getId());
    }

    @Override
    @Transactional
    public Quest updateQuestInfo(UpdateQuestInfoRequestDto dto) {
        QuestEntity questEntity = jpaQuestRepository.findById(dto.id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퀘스트입니다."));

        Quest quest = questEntity.toQuest();
        quest.updateQuestInfo(dto.questDescription()
                , dto.trainingDescription()
                , dto.rewardExp()
                , dto.rewardPoint()
                , dto.guideUrl());

        QuestEntity updateQuest = new QuestEntity(quest);
        QuestEntity saved = jpaQuestRepository.save(updateQuest);
        return saved.toQuest();
    }

    @Override
    @Transactional
    public List<QuestOpenCondition> insertOpenConditions(Quest node, List<Quest> openConditions) {
        if (openConditions.isEmpty()) {
            return new ArrayList<>();
        }

        List<QuestOpenCondition> questOpenConditions = new ArrayList<>();
        for (Quest openCondition : openConditions) {
            questOpenConditions.add(
                    QuestOpenCondition.builder()
                            .node(node)
                            .parents(openCondition)
                            .build()
            );
        }

        List<QuestOpenConditionEntity> save = new ArrayList<>();
        List<QuestOpenConditionEntity> list = questOpenConditions.stream().map(QuestOpenConditionEntity::new).toList();
        save = jpaQuestOpenConditionRepository.saveAll(list);

        return save.stream().map(QuestOpenConditionEntity::toQuestOpenCondition).toList();
    }

}
