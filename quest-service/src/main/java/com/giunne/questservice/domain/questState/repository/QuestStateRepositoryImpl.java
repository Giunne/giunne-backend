package com.giunne.questservice.domain.questState.repository;

import com.giunne.questservice.domain.course.repository.entity.QCourseEntity;
import com.giunne.questservice.domain.quest.repository.entity.QQuestEntity;
import com.giunne.questservice.domain.quest.repository.entity.QQuestOpenConditionEntity;
import com.giunne.questservice.domain.questState.domain.QuestState;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import com.giunne.questservice.domain.questState.repository.entity.QQuestStateEntity;
import com.giunne.questservice.domain.questState.repository.entity.QuestStateEntity;
import com.giunne.questservice.domain.questState.repository.jpa.JpaQuestStateRepository;
import com.giunne.questservice.domain.questState.application.interfaces.QuestStateRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestStateRepositoryImpl implements QuestStateRepository {
    private final JPAQueryFactory queryFactory;
    private final JpaQuestStateRepository jpaQuestStateRepository;
    private final QCourseEntity courseEntity = QCourseEntity.courseEntity;
    private final QQuestEntity qQuestEntity = QQuestEntity.questEntity;
    private final QQuestStateEntity qQuestStateEntity = QQuestStateEntity.questStateEntity;
    private final QQuestOpenConditionEntity qQuestOpenConditionEntity = QQuestOpenConditionEntity.questOpenConditionEntity;

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
        return stateEntities.stream().map(QuestStateEntity::toQuestState).toList();
    }

    @Transactional
    public void updateChildQuestOpen(QuestState questState) {

        if (!QuestProgress.CONFIRM.equals(questState.getQuestProgress())) {
            return;
        }

        List<QuestStateEntity> fetchChildQuestStates = queryFactory
                .selectFrom(qQuestStateEntity)
                .leftJoin(qQuestEntity).on(qQuestStateEntity.quest.id.eq(qQuestEntity.id))
                .leftJoin(qQuestOpenConditionEntity).on(qQuestOpenConditionEntity.node.id.eq(qQuestEntity.id))
                .where(
                        qQuestOpenConditionEntity.parents.id.eq(questState.getQuest().getId())
                                .and(qQuestStateEntity.player.avatarId.eq(questState.getPlayer().getAvatarId()))
                )
                .fetch();

        if (fetchChildQuestStates.isEmpty()) {
            return;
        }

        List<Long> childIds = fetchChildQuestStates.stream().map(QuestStateEntity::getId).toList();

        List<QuestStateEntity> fetchParentQuestStates = queryFactory
                .selectFrom(qQuestStateEntity)
                .leftJoin(qQuestEntity).on(qQuestStateEntity.quest.id.eq(qQuestEntity.id))
                .leftJoin(qQuestOpenConditionEntity).on( qQuestOpenConditionEntity.node.id.eq(qQuestEntity.id)
                )
                .where(
                        qQuestOpenConditionEntity.node.id.in(
                                        JPAExpressions
                                                .select(qQuestOpenConditionEntity.parents.id)
                                                .from(qQuestOpenConditionEntity)
                                                .leftJoin(qQuestStateEntity).on(
                                                        qQuestOpenConditionEntity.node.id.eq(qQuestStateEntity.quest.id)
                                                )
                                                .where(qQuestStateEntity.id.in(childIds))
                                )
                                .and(qQuestStateEntity.player.avatarId.eq(questState.getPlayer().getAvatarId()))
                )
                .fetch();

        boolean isAllQuestStatusConfirm = true;

        // 부모가 모두 CONFIRM 확인
        for (QuestStateEntity fetchParentQuestState : fetchParentQuestStates) {
            if (!QuestProgress.CONFIRM.equals(fetchParentQuestState.getQuestProgress())) {
                isAllQuestStatusConfirm = false;
                break;
            }
        }

        if (!isAllQuestStatusConfirm) {
            return;
        }

        // 다음 자식드 OPEN
        for (QuestStateEntity questStateEntity : fetchChildQuestStates) {
            if (QuestProgress.LOCK.equals(questStateEntity.getQuestProgress())) {
                QuestState childQuestState = questStateEntity.toQuestState();
                if(questStateEntity.getQuestProgress() == QuestProgress.LOCK){
                    childQuestState.updateQuestProgress(QuestProgress.LOCK_OPEN);
                    save(childQuestState);
                }
            }
        }

    }

}
