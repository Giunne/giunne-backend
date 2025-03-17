package com.giunne.questservice.domain.quest.repository;

import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.quest.application.dto.response.UploadQuestInfoResponseDto;
import com.giunne.questservice.domain.quest.application.dto.response.UploadQuestPostInfoResponseDto;
import com.giunne.questservice.domain.quest.application.dto.response.UploadQuestStateInfoResponseDto;
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
import com.giunne.questservice.domain.questPost.repository.entity.QQuestPostEntity;
import com.giunne.questservice.domain.questPost.repository.jpa.JpaQuestPostRepository;
import com.giunne.questservice.domain.questState.application.dto.response.QuestInfoResponseDto;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import com.giunne.questservice.domain.questState.repository.entity.QQuestStateEntity;
import com.giunne.questservice.domain.questState.repository.jpa.JpaQuestStateRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private final JpaQuestPostRepository jpaQuestPostRepository;
    private final QQuestPostEntity qQuestPostEntity = QQuestPostEntity.questPostEntity;


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

    @Override
    public List<UploadQuestInfoResponseDto> findUploadQuests(Long roadMapId) {
        List<Tuple> joinResults = queryFactory
                .select(
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
                        qQuestEntity.currentApproveCount.value,
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

                        qQuestPostEntity.id,
                        qQuestPostEntity.questState.id,
                        qQuestPostEntity.questState.player.id,
                        qQuestPostEntity.questState.player.avatarId,
                        qQuestPostEntity.questPostTitle.value,
                        qQuestPostEntity.questPostContent.value,
                        qQuestPostEntity.questPostProgressType
                )
                .from(qQuestEntity)
                .join(qCourseEntity).on(qQuestEntity.course.id.eq(qCourseEntity.id))
                .join(qQuestStateEntity).on(qQuestEntity.id.eq(qQuestStateEntity.quest.id))
                .join(qQuestPostEntity).on(qQuestPostEntity.questState.id.eq(qQuestStateEntity.id))
                .where(
                        qQuestEntity.course.roadMap.id.eq(roadMapId)
                                .and(qQuestStateEntity.questProgress.eq(
                                                QuestProgress.UPLOAD
                                        )
                                )
                )
                .fetch();

        Map<Long, UploadQuestInfoResponseDto> questMap = new LinkedHashMap<>();

        for (Tuple tuple : joinResults) {

            // 퀘스트 정보 추가
            Long questId = tuple.get(qQuestEntity.id);

            questMap.computeIfAbsent(questId, id -> {
                        UploadQuestInfoResponseDto questInfoDto = new UploadQuestInfoResponseDto();
                        questInfoDto.setId(id);
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
                        questInfoDto.setCurrentApproveCount(tuple.get(qQuestEntity.currentApproveCount.value));
                        questInfoDto.setNeedApproveCount(tuple.get(qQuestEntity.needApproveCount.value));
                        questInfoDto.setRewardPoint(tuple.get(qQuestEntity.rewardPoint.value));
                        questInfoDto.setRewardExp(tuple.get(qQuestEntity.rewardExp.value));
                        questInfoDto.setTrainingDescription(tuple.get(qQuestEntity.trainingDescription.value));
                        questInfoDto.setGuideUrl(tuple.get(qQuestEntity.guideUrl.guideUrl));
                        questInfoDto.setQuestDescription(tuple.get(qQuestEntity.questDescription.value));
                        questInfoDto.setQuestPostInfo(new ArrayList<>());

                        return questInfoDto;
                    }
            );


            // 퀘스트 게시판 추가
            if (tuple.get(qQuestPostEntity.id) != null) {
                UploadQuestPostInfoResponseDto questPostInfoDto = new UploadQuestPostInfoResponseDto();
                questPostInfoDto.setId(tuple.get(qQuestPostEntity.id));
                questPostInfoDto.setPlayerId(tuple.get(qQuestPostEntity.questState.player.id));
                questPostInfoDto.setQuestPostTitle(tuple.get(qQuestPostEntity.questPostTitle.value));
                questPostInfoDto.setQuestPostContent(tuple.get(qQuestPostEntity.questPostContent.value));
                questPostInfoDto.setQuestPostProgressType(tuple.get(qQuestPostEntity.questPostProgressType));
                questMap.get(questId).getQuestPostInfo().add(questPostInfoDto);
            }

            // 퀘스트 게시판 추가
            if (tuple.get(qQuestPostEntity.id) != null) {
                UploadQuestStateInfoResponseDto questState = new UploadQuestStateInfoResponseDto();
                questState.setId(tuple.get(qQuestStateEntity.id));
                questState.setPlayerId(tuple.get(qQuestStateEntity.player.avatarId));
                questState.setQuestProgress(tuple.get(qQuestStateEntity.questProgress));
                questState.setRewardExp(tuple.get(qQuestStateEntity.rewardExp.value));
                questState.setRewardPoint(tuple.get(qQuestStateEntity.rewardPoint.value));
                questState.setStarPoint(tuple.get(qQuestStateEntity.starPoint.value));
                questState.setHasExtraPoints(tuple.get(qQuestStateEntity.hasExtraPoints.value));
                questMap.get(questId).setQuestStateInfo(questState);
            }
        }

        return new ArrayList<>(questMap.values());
    }

}
