package com.giunne.memberservice.domain.notice.repository;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.notice.application.dto.request.GetNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.response.GetNoticeResponseDto;
import com.giunne.memberservice.domain.notice.application.interfaces.NoticeRepository;
import com.giunne.memberservice.domain.notice.domain.Notice;
import com.giunne.memberservice.domain.notice.domain.type.NoticeReadId;
import com.giunne.memberservice.domain.notice.repository.entity.NoticeEntity;
import com.giunne.memberservice.domain.notice.repository.entity.NoticeReadEntity;
import com.giunne.memberservice.domain.notice.repository.entity.QNoticeEntity;
import com.giunne.memberservice.domain.notice.repository.entity.QNoticeReadEntity;
import com.giunne.memberservice.domain.notice.repository.jpa.JpaNoticeReadRepository;
import com.giunne.memberservice.domain.notice.repository.jpa.JpaNoticeRepository;
import com.giunne.memberservice.domain.recreation.domain.Recreation;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
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

import java.util.List;
import java.util.Objects;

import static com.giunne.commonservice.util.PaginationUtil.getPageRequest;
import static com.giunne.commonservice.util.PaginationUtil.toPaginationModel;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepository {
    private final JPAQueryFactory queryFactory;
    private final JpaNoticeRepository jpaNoticeRepository;
    private final JpaNoticeReadRepository jpaNoticeReadRepository;
    private final QNoticeEntity qNoticeEntity = QNoticeEntity.noticeEntity;
    private final QNoticeReadEntity qNoticeReadEntity = QNoticeReadEntity.noticeReadEntity;

    public Long countNotReadNotice(Avatar avatar) {
        return queryFactory
                .select(qNoticeReadEntity.count())
                .from(qNoticeReadEntity)
                .where(
                        qNoticeReadEntity.id.playerId.eq(avatar.getId()),
                        qNoticeReadEntity.isRead.isFalse()
                )
                .fetchOne()
                ;
    }

    public Notice getNotice(Long id) {
        return jpaNoticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지입니다."))
                .toDomain();
    }

    public GetNoticeResponseDto getMyNotice(Long id, Avatar avatar) {
        return queryFactory
                .select(
                        Projections.fields(
                                GetNoticeResponseDto.class,
                                qNoticeEntity.id.as("id"),
                                qNoticeEntity.writer.id.as("writerId"),
                                qNoticeEntity.title,
                                qNoticeEntity.content,
                                qNoticeEntity.createTime,
                                qNoticeEntity.updateTime,
                                qNoticeReadEntity.isRead
                        )
                )
                .from(qNoticeEntity)
                .join(qNoticeReadEntity).on(qNoticeEntity.id.eq(qNoticeReadEntity.id.noticeId))
                .where(
                        qNoticeEntity.id.eq(id),
                        qNoticeReadEntity.id.playerId.eq(avatar.getId())
                )
                .fetchOne();
    }

    @Transactional
    public Notice saveNotice(Notice notice) {
        NoticeEntity save = jpaNoticeRepository.save(new NoticeEntity(notice));
        return save.toDomain();
    }

    @Transactional
    public void initNoticeRead(Notice notice, List<Avatar> avatars) {
        List<NoticeReadEntity> noticeReadEntities = avatars.stream()
                .map(avatar ->
                        {
                            if (Objects.equals(avatar.getId(), notice.getWriter().getId())) {
                                return new NoticeReadEntity(notice.getId(), avatar.getId(), true);
                            }
                            return new NoticeReadEntity(notice.getId(), avatar.getId(), false);
                        }
                )
                .toList();

        jpaNoticeReadRepository.saveAll(noticeReadEntities);
    }

    @Transactional
    public void singUpNoticeRead(Recreation recreation, Avatar avatar) {

        List<NoticeEntity> noticeEntities = queryFactory
                .select(qNoticeEntity)
                .from(qNoticeEntity)
                .where(
                        qNoticeEntity.recreation.id.eq(recreation.getId())
                )
                .fetch();

        List<NoticeReadEntity> noticeReadEntities = noticeEntities.stream()
                .map(noticeEntity -> {
                    if (noticeEntity.getWriter().getId().equals(avatar.getId())) {
                        return new NoticeReadEntity(noticeEntity.getId(), avatar.getId(), true);
                    }
                    return new NoticeReadEntity(noticeEntity.getId(), avatar.getId(), false);
                })
                .toList();

        jpaNoticeReadRepository.saveAll(noticeReadEntities);
    }

    @Transactional
    public void deleteNoticeReadByNoticeId(Long noticeId) {
        jpaNoticeReadRepository.deleteById_NoticeId(noticeId);
    }

    @Transactional
    public void readNotice(Notice notice, Avatar avatar) {

        NoticeReadEntity noticeReadId =  jpaNoticeReadRepository.findById(new NoticeReadId(notice.getId(), avatar.getId()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지입니다."));
        noticeReadId.read();

        jpaNoticeReadRepository.save(noticeReadId);
    }

    @Transactional
    public void deleteById(Long id) {
        jpaNoticeRepository.deleteById(id);
    }

    @Transactional
    public Notice updateNotice(Notice notice) {
        NoticeEntity updateNotice = new NoticeEntity(notice);
        NoticeEntity save = jpaNoticeRepository.save(updateNotice);
        return save.toDomain();
    }

    @Transactional(readOnly = true)
    public PaginationModel<GetNoticeResponseDto> getNoticeList(GetNoticeRequestDto dto, Avatar avatar) {
        Pageable pageable = getPageRequest(
                dto.getPageIndex(),
                dto.getPageSize(),
                Sort.by(Sort.Direction.valueOf(dto.getDirection()), dto.getSortProperty())
        );

        JPAQuery<Long> countQuery = queryFactory
                .select(
                        qNoticeEntity.count()
                )
                .from(qNoticeEntity)
                .where(
                        qNoticeEntity.recreation.id.eq(dto.getRecreationId())
                );

        List<GetNoticeResponseDto> notices = queryFactory
                .select(
                        Projections.fields(
                                GetNoticeResponseDto.class,
                                qNoticeEntity.id.as("id"),
                                qNoticeEntity.writer.id.as("writerId"),
                                qNoticeEntity.title,
                                qNoticeEntity.content,
                                qNoticeEntity.createTime,
                                qNoticeEntity.updateTime,
                                qNoticeReadEntity.isRead
                        )
                )
                .from(qNoticeEntity)
                .join(qNoticeReadEntity).on(qNoticeEntity.id.eq(qNoticeReadEntity.id.noticeId))
                .where(
                        qNoticeEntity.recreation.id.eq(dto.getRecreationId()),
                        qNoticeReadEntity.id.playerId.eq(avatar.getId())
                )
                .orderBy(getOrderSpecifier(dto.getSortProperty(), dto.getDirection()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Page<GetNoticeResponseDto> pageResult = PageableExecutionUtils.getPage(notices, pageable, countQuery::fetchCount);
        return toPaginationModel(pageResult);
    }

    private OrderSpecifier<?> getOrderSpecifier(String property, String direction) {
        Order order = direction.equalsIgnoreCase("ASC") ? Order.ASC : Order.DESC;

        return switch (property) {
            default -> new OrderSpecifier<>(order, qNoticeEntity.id);
        };
    }

}
