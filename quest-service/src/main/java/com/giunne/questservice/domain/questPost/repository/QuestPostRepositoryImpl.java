package com.giunne.questservice.domain.questPost.repository;

import com.giunne.questservice.domain.quest.repository.entity.QQuestEntity;
import com.giunne.questservice.domain.questAttachment.repository.entity.QQuestAttachmentEntity;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostDetailResponseDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostResponseDto;
import com.giunne.questservice.domain.questPost.application.interfaces.QuestPostRepository;
import com.giunne.questservice.domain.questPost.domain.QuestPost;
import com.giunne.questservice.domain.questPost.repository.entity.QQuestPostEntity;
import com.giunne.questservice.domain.questPost.repository.entity.QuestPostEntity;
import com.giunne.questservice.domain.questPost.repository.jpa.JpaQuestPostRepository;
import com.giunne.questservice.domain.questPostAttachment.repository.entity.QQuestPostAttachmentEntity;
import com.giunne.questservice.domain.questState.repository.entity.QQuestStateEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestPostRepositoryImpl implements QuestPostRepository {

    private final JPAQueryFactory queryFactory;
    private final JpaQuestPostRepository jpaQuestPostRepository;
    private static final QQuestPostEntity qQuestPostEntity = QQuestPostEntity.questPostEntity;
    private static final QQuestPostAttachmentEntity qQuestPostAttachmentEntity = QQuestPostAttachmentEntity.questPostAttachmentEntity;
    private static final QQuestStateEntity qQuestStateEntity = QQuestStateEntity.questStateEntity;
    private static final QQuestEntity qQuestEntity = QQuestEntity.questEntity;

    @Transactional
    public QuestPost save(QuestPost questPost) {
        QuestPostEntity save = jpaQuestPostRepository.save(new QuestPostEntity(questPost));
        return save.toQuestPost();
    }

    public GetPostResponseDto findById(Long id) {
        return queryFactory
                .select(
                        Projections.fields(
                                GetPostResponseDto.class,
                                qQuestPostEntity.id.as("id"),
                                qQuestPostEntity.questPostTitle.value.as("title"),
                                qQuestPostEntity.questPostContent.value.as("content"),
                                qQuestPostEntity.questPostProgressType.as("questPostProgressType"),
                                qQuestPostAttachmentEntity.fileUrl.value.as("fileUrl"),
                                qQuestStateEntity.player.avatarId.as("playerId"),
                                qQuestEntity.questType.as("questType")
                        )
                )
                .from(qQuestPostEntity)
                .join(qQuestStateEntity).on(qQuestPostEntity.questState.id.eq(qQuestStateEntity.id))
                .join(qQuestEntity).on(qQuestStateEntity.quest.id.eq(qQuestEntity.id))
                .join(qQuestPostAttachmentEntity).on(qQuestPostEntity.id.eq(qQuestPostAttachmentEntity.questPost.id))
                .where(qQuestPostEntity.id.eq(id))
                .fetchOne();
    }


    public List<GetPostDetailResponseDto> findMyQuest(Long questId, Long playerId) {
        return queryFactory
                .select(
                        Projections.fields(
                                GetPostDetailResponseDto.class,
                                qQuestPostEntity.id.as("id"),
                                qQuestPostEntity.questPostTitle.value.as("title"),
                                qQuestPostEntity.questPostContent.value.as("content"),
                                qQuestPostEntity.questPostProgressType.as("questPostProgressType"),
                                qQuestPostAttachmentEntity.fileUrl.value.as("fileUrl"),
                                qQuestEntity.questType.as("questType")
                        )
                )
                .from(qQuestPostEntity)
                .join(qQuestStateEntity).on(qQuestPostEntity.questState.id.eq(qQuestStateEntity.id))
                .join(qQuestEntity).on(qQuestStateEntity.quest.id.eq(qQuestEntity.id))
                .join(qQuestPostAttachmentEntity).on(qQuestPostEntity.id.eq(qQuestPostAttachmentEntity.questPost.id))
                .where(
                        qQuestPostEntity.id.eq(questId)
                                .and(qQuestPostEntity.player.avatarId.eq(playerId))
                )
                .fetch();
    }

}
