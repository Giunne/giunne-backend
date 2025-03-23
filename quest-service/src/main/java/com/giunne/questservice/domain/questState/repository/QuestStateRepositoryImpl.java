package com.giunne.questservice.domain.questState.repository;

import com.giunne.questservice.domain.course.repository.entity.QCourseEntity;
import com.giunne.questservice.domain.quest.repository.entity.QQuestEntity;
import com.giunne.questservice.domain.quest.repository.entity.QQuestOpenConditionEntity;
import com.giunne.questservice.domain.questPost.repository.entity.QQuestPostEntity;
import com.giunne.questservice.domain.questPost.repository.entity.QuestPostEntity;
import com.giunne.questservice.domain.questState.application.dto.response.QuestInfoResponseDto;
import com.giunne.questservice.domain.questState.application.dto.response.QuestStateInfoResponseDto;
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

import java.util.*;

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
    private final QCourseEntity qCourseEntity = QCourseEntity.courseEntity;
    private final QQuestPostEntity qQuestPostEntity = QQuestPostEntity.questPostEntity;


    @Override
    public QuestState findById(Long id) {
        QuestStateEntity questStateEntity = jpaQuestStateRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 ID입니다.")
        );
        return questStateEntity.toQuestState();
    }

    @Override
    public QuestState findByPlayerAndQuest(Long playerId, Long questId) {
        QuestStateEntity questStateEntity = jpaQuestStateRepository.findByPlayer_AvatarIdAndQuest_Id(playerId, questId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 퀘스트입니다.")
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
                .leftJoin(qQuestOpenConditionEntity).on(qQuestOpenConditionEntity.node.id.eq(qQuestEntity.id)
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
                if (questStateEntity.getQuestProgress() == QuestProgress.LOCK) {
                    childQuestState.updateQuestProgress(QuestProgress.LOCK_OPEN);
                    save(childQuestState);
                }
            }
        }

    }

    @Override
    public List<QuestInfoResponseDto> findInProgressQuestByRoadMap(Long roadMapId, Long avatarId) {
        List<Tuple> joinResults = queryFactory
                .select(
                        qCourseEntity.id,
                        qCourseEntity.thumbnailUrl.thumbnailUrl,

                        qQuestEntity.id,
                        qQuestEntity.questName.value,
                        qQuestEntity.deadline,
                        qQuestEntity.needLevel.value,
                        qQuestEntity.difficultyLevel.value,
                        qQuestEntity.isTeam.value,
                        qQuestEntity.cooperationType,
                        qQuestEntity.trainingType,
                        qQuestEntity.minPlayer.value,
                        qQuestEntity.maxPlayer.value,
                        qQuestEntity.sortSeq.value,
                        qQuestEntity.questType,
                        qQuestEntity.needApproveCount.value,
                        qQuestEntity.rewardPoint.value,
                        qQuestEntity.rewardExp.value,
                        qQuestEntity.trainingDescription.value,
                        qQuestEntity.guideUrl.guideUrl,
                        qQuestEntity.questDescription.value,
                        qQuestEntity.startQuestProgress,
                        qQuestStateEntity.id,
                        qQuestStateEntity.player.avatarId,
                        qQuestStateEntity.questProgress,
                        qQuestStateEntity.rewardPoint.value,
                        qQuestStateEntity.rewardExp.value,
                        qQuestStateEntity.starPoint.value,
                        qQuestStateEntity.hasExtraPoints.value,
                        qQuestStateEntity.currentApproveCount.value
                )
                .from(qCourseEntity)
                .join(qQuestEntity).on(qQuestEntity.course.id.eq(qCourseEntity.id))
                .join(qQuestStateEntity).on(qQuestEntity.id.eq(qQuestStateEntity.quest.id))
                .where(
                        qQuestEntity.course.roadMap.id.eq(roadMapId)
                                .and(qQuestStateEntity.player.avatarId.eq(avatarId))
                                .and(qQuestStateEntity.questProgress.in(
                                                QuestProgress.LOCK_OPEN,
                                                QuestProgress.CHECK,
                                                QuestProgress.UPLOAD
                                        )
                                )
                                .and(qQuestEntity.startQuestProgress.ne(QuestProgress.CONFIRM))
                )
                .fetch();

        Map<Long, QuestInfoResponseDto> questMap = new LinkedHashMap<>();
        for (Tuple tuple : joinResults) {

            // 퀘스트 정보 추가
            Long questId = tuple.get(qQuestEntity.id);

            questMap.computeIfAbsent(questId, id -> {
                        QuestInfoResponseDto questInfoDto = new QuestInfoResponseDto();
                        questInfoDto.setId(id);
                        questInfoDto.setThumbnailUrl(tuple.get(qCourseEntity.thumbnailUrl.thumbnailUrl));
                        questInfoDto.setQuestName(tuple.get(qQuestEntity.questName.value));
                        questInfoDto.setDeadline(tuple.get(qQuestEntity.deadline));
                        questInfoDto.setNeedLevel(tuple.get(qQuestEntity.needLevel.value));
                        questInfoDto.setDifficultyLevel(tuple.get(qQuestEntity.difficultyLevel.value));
                        questInfoDto.setIsTeam(tuple.get(qQuestEntity.isTeam.value));
                        questInfoDto.setCooperationType(tuple.get(qQuestEntity.cooperationType));
                        questInfoDto.setTrainingType(tuple.get(qQuestEntity.trainingType));
                        questInfoDto.setMaxPlayer(tuple.get(qQuestEntity.maxPlayer.value));
                        questInfoDto.setMinPlayer(tuple.get(qQuestEntity.minPlayer.value));
                        questInfoDto.setSortSeq(tuple.get(qQuestEntity.sortSeq.value));
                        questInfoDto.setQuestType(tuple.get(qQuestEntity.questType));
                        questInfoDto.setNeedApproveCount(tuple.get(qQuestEntity.needApproveCount.value));
                        questInfoDto.setRewardPoint(tuple.get(qQuestEntity.rewardPoint.value));
                        questInfoDto.setRewardExp(tuple.get(qQuestEntity.rewardExp.value));
                        questInfoDto.setTrainingDescription(tuple.get(qQuestEntity.trainingDescription.value));
                        questInfoDto.setGuideUrl(tuple.get(qQuestEntity.guideUrl.guideUrl));
                        questInfoDto.setQuestDescription(tuple.get(qQuestEntity.questDescription.value));
                        return questInfoDto;
                    }
            );

            // 퀘스트 상태 추가
            if (tuple.get(qQuestStateEntity.id) != null) {
                QuestInfoResponseDto questInfoResponseDto = questMap.get(questId);
                QuestStateInfoResponseDto questState = new QuestStateInfoResponseDto();
                questState.setId(tuple.get(qQuestStateEntity.id));
                questState.setPlayerId(tuple.get(qQuestStateEntity.player.avatarId));
                questState.setQuestProgress(tuple.get(qQuestStateEntity.questProgress));
                questState.setRewardExp(tuple.get(qQuestStateEntity.rewardExp.value));
                questState.setRewardPoint(tuple.get(qQuestStateEntity.rewardPoint.value));
                questState.setStarPoint(tuple.get(qQuestStateEntity.starPoint.value));
                questState.setHasExtraPoints(tuple.get(qQuestStateEntity.hasExtraPoints.value));
                questState.setCurrentApproveCount(tuple.get(qQuestStateEntity.currentApproveCount.value));
                questInfoResponseDto.setQuestStateInfo(questState);
            }
        }

        return new ArrayList<>(questMap.values());
    }

    @Override
    public List<QuestInfoResponseDto> findConfirmQuestByRoadMap(Long roadMapId, Long avatarId) {
        List<Tuple> joinResults = queryFactory
                .select(
                        qCourseEntity.id,
                        qCourseEntity.thumbnailUrl.thumbnailUrl,

                        qQuestEntity.id,
                        qQuestEntity.questName.value,
                        qQuestEntity.deadline,
                        qQuestEntity.needLevel.value,
                        qQuestEntity.difficultyLevel.value,
                        qQuestEntity.isTeam.value,
                        qQuestEntity.cooperationType,
                        qQuestEntity.trainingType,
                        qQuestEntity.minPlayer.value,
                        qQuestEntity.maxPlayer.value,
                        qQuestEntity.sortSeq.value,
                        qQuestEntity.questType,
                        qQuestEntity.needApproveCount.value,
                        qQuestEntity.rewardPoint.value,
                        qQuestEntity.rewardExp.value,
                        qQuestEntity.trainingDescription.value,
                        qQuestEntity.guideUrl.guideUrl,
                        qQuestEntity.questDescription.value,
                        qQuestEntity.startQuestProgress,
                        qQuestStateEntity.id,
                        qQuestStateEntity.player.avatarId,
                        qQuestStateEntity.questProgress,
                        qQuestStateEntity.rewardPoint.value,
                        qQuestStateEntity.rewardExp.value,
                        qQuestStateEntity.starPoint.value,
                        qQuestStateEntity.hasExtraPoints.value,
                        qQuestStateEntity.currentApproveCount.value
                )
                .from(qCourseEntity)
                .join(qQuestEntity).on(qQuestEntity.course.id.eq(qCourseEntity.id))
                .join(qQuestStateEntity).on(qQuestEntity.id.eq(qQuestStateEntity.quest.id))
                .where(
                        qQuestEntity.course.roadMap.id.eq(roadMapId)
                                .and(qQuestStateEntity.player.avatarId.eq(avatarId))
                                .and(qQuestStateEntity.questProgress.in(
                                                QuestProgress.CONFIRM
                                        )
                                )
                                .and(qQuestEntity.startQuestProgress.ne(QuestProgress.CONFIRM))
                )
                .fetch();

        Map<Long, QuestInfoResponseDto> questMap = new LinkedHashMap<>();
        for (Tuple tuple : joinResults) {

            // 퀘스트 정보 추가
            Long questId = tuple.get(qQuestEntity.id);

            questMap.computeIfAbsent(questId, id -> {
                        QuestInfoResponseDto questInfoDto = new QuestInfoResponseDto();
                        questInfoDto.setId(id);
                        questInfoDto.setThumbnailUrl(tuple.get(qCourseEntity.thumbnailUrl.thumbnailUrl));
                        questInfoDto.setQuestName(tuple.get(qQuestEntity.questName.value));
                        questInfoDto.setDeadline(tuple.get(qQuestEntity.deadline));
                        questInfoDto.setNeedLevel(tuple.get(qQuestEntity.needLevel.value));
                        questInfoDto.setDifficultyLevel(tuple.get(qQuestEntity.difficultyLevel.value));
                        questInfoDto.setIsTeam(tuple.get(qQuestEntity.isTeam.value));
                        questInfoDto.setCooperationType(tuple.get(qQuestEntity.cooperationType));
                        questInfoDto.setTrainingType(tuple.get(qQuestEntity.trainingType));
                        questInfoDto.setMaxPlayer(tuple.get(qQuestEntity.maxPlayer.value));
                        questInfoDto.setMinPlayer(tuple.get(qQuestEntity.minPlayer.value));
                        questInfoDto.setSortSeq(tuple.get(qQuestEntity.sortSeq.value));
                        questInfoDto.setQuestType(tuple.get(qQuestEntity.questType));
                        questInfoDto.setNeedApproveCount(tuple.get(qQuestEntity.needApproveCount.value));
                        questInfoDto.setRewardPoint(tuple.get(qQuestEntity.rewardPoint.value));
                        questInfoDto.setRewardExp(tuple.get(qQuestEntity.rewardExp.value));
                        questInfoDto.setTrainingDescription(tuple.get(qQuestEntity.trainingDescription.value));
                        questInfoDto.setGuideUrl(tuple.get(qQuestEntity.guideUrl.guideUrl));
                        questInfoDto.setQuestDescription(tuple.get(qQuestEntity.questDescription.value));
                        return questInfoDto;
                    }
            );

            // 퀘스트 상태 추가
            if (tuple.get(qQuestStateEntity.id) != null) {
                QuestInfoResponseDto questInfoResponseDto = questMap.get(questId);
                QuestStateInfoResponseDto questState = new QuestStateInfoResponseDto();
                questState.setId(tuple.get(qQuestStateEntity.id));
                questState.setPlayerId(tuple.get(qQuestStateEntity.player.avatarId));
                questState.setQuestProgress(tuple.get(qQuestStateEntity.questProgress));
                questState.setRewardExp(tuple.get(qQuestStateEntity.rewardExp.value));
                questState.setRewardPoint(tuple.get(qQuestStateEntity.rewardPoint.value));
                questState.setStarPoint(tuple.get(qQuestStateEntity.starPoint.value));
                questState.setHasExtraPoints(tuple.get(qQuestStateEntity.hasExtraPoints.value));
                questState.setCurrentApproveCount(tuple.get(qQuestStateEntity.currentApproveCount.value));
                questInfoResponseDto.setQuestStateInfo(questState);
            }
        }

        return new ArrayList<>(questMap.values());
    }

    public QuestState findByQuestPostId(Long questPostId) {

        QuestStateEntity questStateEntity = queryFactory
                .selectFrom(qQuestStateEntity)
                .join(qQuestPostEntity).on(qQuestStateEntity.id.eq(qQuestPostEntity.questState.id))
                .where(
                        qQuestPostEntity.id.eq(questPostId)
                )
                .fetchOne();
        return questStateEntity.toQuestState();
    }

}
