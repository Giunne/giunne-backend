package com.giunne.questservice.domain.questState.repository;

import com.giunne.questservice.domain.course.repository.entity.CourseEntity;
import com.giunne.questservice.domain.course.repository.entity.QCourseEntity;
import com.giunne.questservice.domain.course.repository.entity.QCourseParentEntity;
import com.giunne.questservice.domain.course.repository.entity.QCoursePathEntity;
import com.giunne.questservice.domain.course.repository.jpa.JpaCourseParentRepository;
import com.giunne.questservice.domain.course.repository.jpa.JpaCoursePathRepository;
import com.giunne.questservice.domain.course.repository.jpa.JpaCourseRepository;
import com.giunne.questservice.domain.quest.repository.entity.QQuestEntity;
import com.giunne.questservice.domain.questState.domain.QuestState;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import com.giunne.questservice.domain.questState.repository.entity.QQuestStateEntity;
import com.giunne.questservice.domain.questState.repository.entity.QuestStateEntity;
import com.giunne.questservice.domain.questState.repository.jpa.JpaQuestStateRepository;
import com.giunne.questservice.domain.questState.application.interfaces.QuestStateRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestStateRepositoryImpl implements QuestStateRepository {
    private final JPAQueryFactory queryFactory;
    private final JpaCourseRepository courseRepository;
    private final JpaCoursePathRepository coursePathRepository;
    private final JpaQuestStateRepository jpaQuestStateRepository;
    private final QCourseEntity courseEntity = QCourseEntity.courseEntity;
    private final QCoursePathEntity qCoursePathEntity = QCoursePathEntity.coursePathEntity;
    private final QQuestEntity qQuestEntity = QQuestEntity.questEntity;
    private final QCourseParentEntity qCourseParentEntity = QCourseParentEntity.courseParentEntity;
    private final QQuestStateEntity qQuestStateEntity = QQuestStateEntity.questStateEntity;
    private final JpaCourseParentRepository jpaCourseParentRepository;

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
                .select(qQuestStateEntity)
                .from(courseEntity)
                .leftJoin(qCourseParentEntity).on(courseEntity.id.eq(qCourseParentEntity.node.id))
                .leftJoin(qQuestEntity).on(courseEntity.id.eq(qQuestEntity.course.id))
                .leftJoin(qQuestStateEntity).on(qQuestEntity.id.eq(qQuestStateEntity.quest.id))
                .where(
                        qCourseParentEntity.parents.id.in(
                                        JPAExpressions
                                                .select(qCourseParentEntity.node.id)
                                                .from(courseEntity)
                                                .leftJoin(qCourseParentEntity).on(courseEntity.id.eq(qCourseParentEntity.node.id))
                                                .leftJoin(qQuestEntity).on(courseEntity.id.eq(qQuestEntity.course.id))
                                                .leftJoin(qQuestStateEntity).on(qQuestEntity.id.eq(qQuestStateEntity.quest.id))
                                                .where(
                                                        qQuestStateEntity.id.eq(questState.getId())
                                                                .or(
                                                                        (qQuestStateEntity.questProgress.eq(QuestProgress.CONFIRM))
                                                                                .and(qQuestStateEntity.player.avatarId.eq(questState.getPlayer().getAvatarId()))
                                                                )
                                                )
                                )
                                .and(qQuestStateEntity.player.avatarId.eq(questState.getPlayer().getAvatarId()))
                )
                .fetch();


        List<Long> childIds = fetchChildQuestStates.stream().map(QuestStateEntity::getId).toList();

        List<QuestStateEntity> fetchParentQuestStates = queryFactory
                .select(qQuestStateEntity)
                .from(courseEntity)
                .leftJoin(qCourseParentEntity).on(courseEntity.id.eq(qCourseParentEntity.node.id))
                .leftJoin(qQuestEntity).on(courseEntity.id.eq(qQuestEntity.course.id))
                .leftJoin(qQuestStateEntity).on(qQuestEntity.id.eq(qQuestStateEntity.quest.id))
                .where(
                        qCourseParentEntity.node.id.in(
                                        JPAExpressions
                                                .select(qCourseParentEntity.parents.id)
                                                .from(courseEntity)
                                                .leftJoin(qCourseParentEntity).on(courseEntity.id.eq(qCourseParentEntity.node.id))
                                                .leftJoin(qQuestEntity).on(courseEntity.id.eq(qQuestEntity.course.id))
                                                .leftJoin(qQuestStateEntity).on(qQuestEntity.id.eq(qQuestStateEntity.quest.id))
                                                .where(qQuestStateEntity.id.in(childIds))
                                )
                                .and(qQuestStateEntity.player.avatarId.eq(questState.getPlayer().getAvatarId()))
                )
                .fetch();

        boolean isALlConfirm = true;

        for (QuestStateEntity fetchParentQuestState : fetchParentQuestStates) {
            if (!QuestProgress.CONFIRM.equals(fetchParentQuestState.getQuestProgress())) {
                isALlConfirm = false;
                break;
            }
        }

        if (!isALlConfirm) {
            return;
        }

        for (QuestStateEntity questStateEntity : fetchChildQuestStates) {
            if (QuestProgress.LOCK.equals(questStateEntity.getQuestProgress())) {
                QuestState childQuestState = questStateEntity.toQuestState();
                childQuestState.updateQuestProgress(QuestProgress.LOCK_OPEN);
                save(childQuestState);
            }
        }

    }

}
