package com.giunne.questservice.domain.questPost.repository;

import com.giunne.commonservice.domain.common.Pageable;
import com.giunne.commonservice.infra.external.domain.member.client.MemberInfoClient;
import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyRecreationAvatarResponseDto;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.player.repository.entity.QPlayerEntity;
import com.giunne.questservice.domain.questPost.application.dto.response.GetQuestCommentResponseDto;
import com.giunne.questservice.domain.questPost.application.interfaces.QuestPostCommentRepository;
import com.giunne.questservice.domain.questPost.domain.QuestPostComment;
import com.giunne.questservice.domain.questPost.repository.entity.QQuestPostCommentEntity;
import com.giunne.questservice.domain.questPost.repository.entity.QQuestPostCommentLikeEntity;
import com.giunne.questservice.domain.questPost.repository.entity.QuestPostCommentEntity;
import com.giunne.questservice.domain.questPost.repository.jpa.JpaQuestPostCommentRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.giunne.commonservice.util.PaginationUtil.getPageRequest;
import static com.giunne.commonservice.util.PaginationUtil.toPaginationModel;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestPostCommentRepositoryImpl implements QuestPostCommentRepository {

    private final JPAQueryFactory queryFactory;
    private static final QQuestPostCommentEntity commentEntity = QQuestPostCommentEntity.questPostCommentEntity;
    private static final QPlayerEntity playerEntity = QPlayerEntity.playerEntity;
    private static final QQuestPostCommentLikeEntity likeEntity = QQuestPostCommentLikeEntity.questPostCommentLikeEntity;
    private final JpaQuestPostCommentRepository jpaQuestPostCommentRepository;
    private final MemberInfoClient memberInfoClient;


    @Override
    public QuestPostComment findById(Long id) {
        QuestPostCommentEntity entity = jpaQuestPostCommentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        return entity.toQuestPostComment();
    }

    @Override
    @Transactional
    public QuestPostComment save(QuestPostComment comment) {
        if (comment.getId() != null) {
            jpaQuestPostCommentRepository.updateComment(comment);
            return comment;
        }
        QuestPostCommentEntity save = jpaQuestPostCommentRepository.save(new QuestPostCommentEntity(comment));
        return save.toQuestPostComment();
    }

    public PaginationModel<GetQuestCommentResponseDto> getCommentList(Long postId, Long playerId, Pageable dto) {

        org.springframework.data.domain.Pageable pageable = getPageRequest(
                dto.getPageIndex(),
                dto.getPageSize(),
                Sort.by(Sort.Direction.valueOf(dto.getDirection()), dto.getSortProperty())
        );

        JPAQuery<Long> countQuery = queryFactory
                .select(
                        commentEntity.count()
                )
                .from(commentEntity)
                .join(playerEntity).on(commentEntity.player.id.eq(playerEntity.id))
                .leftJoin(likeEntity).on(hasLike(playerId))
                .where(
                        commentEntity.post.id.eq(postId)
                );

        List<GetQuestCommentResponseDto> fetch = queryFactory
                .select(
                        Projections.fields(
                                GetQuestCommentResponseDto.class,
                                commentEntity.id.as("id"),
                                commentEntity.content.value.as("content"),
                                playerEntity.avatarId.as("playerId"),
                                commentEntity.likeCounter.value.as("likeCount"),
                                commentEntity.createTime.as("createTime"),
                                commentEntity.updateTime.as("updateTime"),
                                likeEntity.isNotNull().as("isLikedByMe")
                        )
                )
                .from(commentEntity)
                .join(playerEntity).on(commentEntity.player.id.eq(playerEntity.id))
                .leftJoin(likeEntity).on(hasLike(playerId))
                .where(
                        commentEntity.post.id.eq(postId)
                )
                .orderBy(commentEntity.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> playerIdlist = fetch.stream().map(GetQuestCommentResponseDto::getPlayerId).toList();

        Response<List<GetMyRecreationAvatarResponseDto>> avatarProfileListInfo = memberInfoClient.getAvatarProfileListInfo(playerIdlist);

        // playerId를 키로, playerInfo를 값으로 저장하는 Map 생성
        Map<Long, GetMyRecreationAvatarResponseDto> playerInfoMap = avatarProfileListInfo.value().stream()
                .collect(Collectors.toMap(GetMyRecreationAvatarResponseDto::getId, Function.identity()));

        // uploadQuests에 playerInfo 매핑
        for (GetQuestCommentResponseDto comment : fetch) {
            Long id = comment.getPlayerId();
            GetMyRecreationAvatarResponseDto playerInfo = playerInfoMap.get(id);

            if (playerInfo != null) {
                comment.setPlayerInfo(playerInfo);
            }
        }

        Page<GetQuestCommentResponseDto> pageResult = PageableExecutionUtils.getPage(fetch, pageable, countQuery::fetchCount);

        return toPaginationModel(pageResult);
    }

    private BooleanExpression hasLike(Long playerId) {
        if (playerId == null) {
            return Expressions.FALSE;
        }
        return commentEntity.id
                .eq(likeEntity.id.targetId)
                .and(likeEntity.id.playerId.eq(playerId));
    }

    private BooleanExpression hasLastData(Long lastId) {
        if (lastId == null) {
            return null;
        }

        return commentEntity.id.lt(lastId);
    }

}
