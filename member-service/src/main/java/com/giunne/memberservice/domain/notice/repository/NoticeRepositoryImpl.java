package com.giunne.memberservice.domain.notice.repository;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.notice.application.dto.request.GetNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.request.UpdateNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.response.GetNoticeResponseDto;
import com.giunne.memberservice.domain.notice.application.interfaces.NoticeRepository;
import com.giunne.memberservice.domain.notice.domain.Notice;
import com.giunne.memberservice.domain.notice.repository.entity.NoticeEntity;
import com.giunne.memberservice.domain.notice.repository.entity.QNoticeEntity;
import com.giunne.memberservice.domain.notice.repository.jpa.JpaNoticeRepository;
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

import static com.giunne.commonservice.util.PaginationUtil.getPageRequest;
import static com.giunne.commonservice.util.PaginationUtil.toPaginationModel;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepository {
    private final JPAQueryFactory queryFactory;
    private final JpaNoticeRepository jpaNoticeRepository;
    private final QNoticeEntity qNoticeEntity = QNoticeEntity.noticeEntity;

    public Notice getNotice(Long id) {
        return jpaNoticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지입니다."))
                .toDomain();
    }

    @Transactional
    public Notice saveNotice(Notice notice) {
        NoticeEntity save = jpaNoticeRepository.save(new NoticeEntity(notice));
        return save.toDomain();
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
    public PaginationModel<GetNoticeResponseDto> getNoticeList(GetNoticeRequestDto dto) {
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
                                qNoticeEntity.updateTime
                        )
                )
                .from(qNoticeEntity)
                .where(
                        qNoticeEntity.recreation.id.eq(dto.getRecreationId())
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
