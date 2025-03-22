package com.giunne.questservice.domain.questPost.repository;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.questservice.domain.player.repository.entity.QPlayerEntity;
import com.giunne.questservice.domain.quest.application.dto.request.GetQuestTypeSearchRequestDto;
import com.giunne.questservice.domain.quest.application.dto.request.GetUploadQuestForStudentRequestDto;
import com.giunne.questservice.domain.quest.application.dto.request.GetUploadQuestRequestDto;
import com.giunne.questservice.domain.quest.application.dto.response.GetUploadQuestResponseDto;
import com.giunne.questservice.domain.quest.repository.entity.QQuestEntity;
import com.giunne.questservice.domain.questAttachment.repository.entity.QQuestAttachmentEntity;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostDetailResponseDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostResponseDto;
import com.giunne.questservice.domain.questPost.application.interfaces.QuestPostRepository;
import com.giunne.questservice.domain.questPost.domain.QuestPost;
import com.giunne.questservice.domain.questPost.repository.entity.QQuestPostCommentEntity;
import com.giunne.questservice.domain.questPost.repository.entity.QQuestPostEntity;
import com.giunne.questservice.domain.questPost.repository.entity.QuestPostEntity;
import com.giunne.questservice.domain.questPost.repository.jpa.JpaQuestPostRepository;
import com.giunne.questservice.domain.questPostAttachment.repository.entity.QQuestPostAttachmentEntity;
import com.giunne.questservice.domain.questState.application.dto.response.QuestInfoResponseDto;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import com.giunne.questservice.domain.questState.repository.entity.QQuestStateEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.giunne.commonservice.util.PaginationUtil.getPageRequest;
import static com.giunne.commonservice.util.PaginationUtil.toPaginationModel;

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
    private static final QQuestPostCommentEntity qQuestPostCommentEntity = QQuestPostCommentEntity.questPostCommentEntity;
    private static final QPlayerEntity qPlayerEntity = QPlayerEntity.playerEntity;


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
                                qQuestPostEntity.createTime.as("createTime"),
                                qQuestPostEntity.updateTime.as("updateTime"),
                                qQuestPostAttachmentEntity.fileUrl.value.as("fileUrl"),
                                qQuestStateEntity.player.avatarId.as("playerId"),
                                qQuestEntity.id.as("questId")
                        )
                )
                .from(qQuestPostEntity)
                .join(qQuestStateEntity).on(qQuestPostEntity.questState.id.eq(qQuestStateEntity.id))
                .join(qQuestEntity).on(qQuestStateEntity.quest.id.eq(qQuestEntity.id))
                .join(qQuestPostAttachmentEntity).on(qQuestPostEntity.id.eq(qQuestPostAttachmentEntity.questPost.id))
                .where(qQuestPostEntity.id.eq(id))
                .fetchOne();
    }

    @Override
    public QuestPost getPost(Long id) {
        QuestPostEntity postEntity = jpaQuestPostRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return postEntity.toQuestPost();
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
                                qQuestPostEntity.createTime.as("createTime"),
                                qQuestPostEntity.updateTime.as("updateTime"),
                                qQuestPostAttachmentEntity.fileUrl.value.as("fileUrl"),
                                qQuestStateEntity.player.avatarId.as("playerId"),
                                qQuestEntity.id.as("questId")
                        )
                )
                .from(qQuestPostEntity)
                .join(qQuestStateEntity).on(qQuestPostEntity.questState.id.eq(qQuestStateEntity.id))
                .join(qQuestEntity).on(qQuestStateEntity.quest.id.eq(qQuestEntity.id))
                .join(qQuestPostAttachmentEntity).on(qQuestPostEntity.id.eq(qQuestPostAttachmentEntity.questPost.id))
                .where(
                        qQuestEntity.id.eq(questId)
                                .and(qQuestPostEntity.player.avatarId.eq(playerId))
                )
                .fetch();
    }


    public PaginationModel<GetUploadQuestResponseDto> findUploadQuest(GetUploadQuestForStudentRequestDto dto) {
        Pageable pageable = getPageRequest(
                dto.getPageIndex(),
                dto.getPageSize(),
                Sort.by(Sort.Direction.valueOf(dto.getDirection()), dto.getSortProperty())
        );

        BooleanBuilder whereClause = new BooleanBuilder();
        Optional.ofNullable(equalToQuestName(dto.getQuestName())).ifPresent(whereClause::and);
        Optional.ofNullable(likeNickName(dto.getNickName())).ifPresent(whereClause::and);

        JPAQuery<Long> count = queryFactory
                .select(qQuestPostEntity.count())
                .from(qQuestPostEntity)
                .join(qQuestStateEntity).on(qQuestPostEntity.questState.id.eq(qQuestStateEntity.id))
                .join(qQuestEntity).on(qQuestStateEntity.quest.id.eq(qQuestEntity.id))
                .join(qPlayerEntity).on(qQuestPostEntity.player.id.eq(qPlayerEntity.id))
                .where(
                        whereClause
                );


        List<Tuple> joinResults = queryFactory
                .select(
                        qQuestEntity.id,
                        qQuestEntity.questName.value,
                        qQuestEntity.questType,
                        qQuestEntity.cooperationType,
                        qQuestEntity.trainingType,
                        qQuestEntity.needApproveCount.value,
                        qQuestEntity.currentApproveCount.value,

                        qQuestPostEntity.id,
                        qQuestPostEntity.questPostProgressType,
                        qQuestPostEntity.createTime,
                        qQuestPostEntity.updateTime,
                        qQuestStateEntity.questProgress,

                        qPlayerEntity.id,
                        qPlayerEntity.avatarId,
                        qPlayerEntity.nickname,

                        qPlayerEntity.avatarId,
                        qPlayerEntity.avatarNickname

                )
                .from(qQuestPostEntity)
                .join(qQuestStateEntity).on(qQuestPostEntity.questState.id.eq(qQuestStateEntity.id))
                .join(qQuestEntity).on(qQuestStateEntity.quest.id.eq(qQuestEntity.id))
                .join(qPlayerEntity).on(qQuestPostEntity.player.id.eq(qPlayerEntity.id))
                .where(
                        whereClause
                )
                .orderBy(getOrderSpecifier(dto.getSortProperty(), dto.getDirection()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> postIds = joinResults.stream()
                .map(tuple -> tuple.get(qQuestPostEntity.id))
                .filter(Objects::nonNull)
                .toList();

        Map<Long, Long> commentCountMap = postIds.isEmpty()
                ? new HashMap<>()
                : queryFactory
                .select(
                        qQuestPostCommentEntity.post.id,
                        qQuestPostCommentEntity.count()
                )
                .from(qQuestPostCommentEntity)
                .where(qQuestPostCommentEntity.post.id.in(postIds))
                .groupBy(qQuestPostCommentEntity.post.id)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(0, Long.class),
                        tuple -> tuple.get(1, Long.class)
                ));

        Map<Long, GetUploadQuestResponseDto> postMap = new LinkedHashMap<>();
        for (Tuple tuple : joinResults) {
            // 퀘스트 정보 추가
            Long postId = tuple.get(qQuestPostEntity.id);

            postMap.computeIfAbsent(postId, id ->
                    GetUploadQuestResponseDto.builder()
                            .id(postId)
                            .questName(tuple.get(qQuestEntity.questName.value))
                            .questType(tuple.get(qQuestEntity.questType))
                            .cooperationType(tuple.get(qQuestEntity.cooperationType))
                            .trainingType(tuple.get(qQuestEntity.trainingType))
                            .needApproveCount(tuple.get(qQuestEntity.needApproveCount.value))
                            .currentApproveCount(tuple.get(qQuestEntity.currentApproveCount.value))
                            .questPostId(tuple.get(qQuestPostEntity.id))
                            .questPostProgressType(tuple.get(qQuestPostEntity.questPostProgressType))
                            .createTime(tuple.get(qQuestPostEntity.createTime))
                            .updateTime(tuple.get(qQuestPostEntity.updateTime))
                            .questProgress(tuple.get(qQuestStateEntity.questProgress))
                            .avatarId(tuple.get(qPlayerEntity.avatarId))
                            .nickname(tuple.get(qPlayerEntity.avatarNickname))
                            .commentCount(commentCountMap.getOrDefault(postId, 0L))
                            .build()
            );
        }

        List<GetUploadQuestResponseDto> list = new ArrayList<>(postMap.values());
        Page<GetUploadQuestResponseDto> pageResult = PageableExecutionUtils.getPage(list, pageable, count::fetchCount);

        return toPaginationModel(pageResult);
    }

    private BooleanExpression likeNickName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        return qPlayerEntity.avatarNickname.like(name + "%");
    }

    private BooleanExpression equalToQuestName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        return qQuestEntity.questName.value.eq(name);
    }

    private OrderSpecifier<?> getOrderSpecifier(String property, String direction) {
        Order order = direction.equalsIgnoreCase("ASC") ? Order.ASC : Order.DESC;
        return new OrderSpecifier<>(order, qQuestPostEntity.updateTime);
    }
}
