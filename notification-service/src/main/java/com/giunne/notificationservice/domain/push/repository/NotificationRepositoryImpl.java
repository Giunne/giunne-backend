package com.giunne.notificationservice.domain.push.repository;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.notificationservice.domain.push.application.dto.response.GetNotificationResponseDto;
import com.giunne.notificationservice.domain.push.application.interfaces.NotificationRepository;
import com.giunne.notificationservice.domain.push.domain.Notification;
import com.giunne.notificationservice.domain.push.repository.entity.NotificationEntity;
import com.giunne.notificationservice.domain.push.repository.entity.QNotificationEntity;
import com.giunne.notificationservice.domain.push.repository.jpa.JpaNotificationRepository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.giunne.commonservice.domain.common.Pageable;
import org.springframework.data.domain.Page;
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
public class NotificationRepositoryImpl implements NotificationRepository {
    private final JPAQueryFactory queryFactory;
    private final JpaNotificationRepository jpaNotificationRepository;
    private final QNotificationEntity qNotificationEntity = QNotificationEntity.notificationEntity;

    @Transactional
    public void saveNotification(Notification notification) {
        jpaNotificationRepository.save(new NotificationEntity(notification));
    }

    @Transactional
    public void readAllNotificationByTargetId(Long targetId) {
        jpaNotificationRepository.readAllNotificationByTargetId(targetId);
    }

    @Transactional
    public void readNotificationById(Long targetId) {
        jpaNotificationRepository.readNotificationById(targetId);
    }

    public Integer countNotReadNotification(Long memberId) {
        return jpaNotificationRepository.countByTargetIdAndIsReadFalse(memberId);
    }

    public PaginationModel<GetNotificationResponseDto> getNotifications(Long memberId, Pageable dto) {
        org.springframework.data.domain.Pageable pageable = getPageRequest(
                dto.getPageIndex(),
                dto.getPageSize(),
                Sort.by(Sort.Direction.valueOf(dto.getDirection()), dto.getSortProperty())
        );

        JPAQuery<Long> countQuery = queryFactory
                .select(
                        qNotificationEntity.count()
                )
                .from(qNotificationEntity)
                .where(
                        qNotificationEntity.targetId.eq(memberId)
                );

        List<GetNotificationResponseDto> notifications = queryFactory
                .select(
                        Projections.fields(
                                GetNotificationResponseDto.class,
                                qNotificationEntity.id,
                                qNotificationEntity.targetId,
                                qNotificationEntity.senderId,
                                qNotificationEntity.title,
                                qNotificationEntity.content,
                                qNotificationEntity.notificationType,
                                qNotificationEntity.referenceId,
                                qNotificationEntity.isRead,
                                qNotificationEntity.createTime,
                                qNotificationEntity.updateTime
                        )
                )
                .from(qNotificationEntity)
                .where(
                        qNotificationEntity.targetId.eq(memberId)
                )
                .orderBy(getOrderSpecifier(dto.getSortProperty(), dto.getDirection()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Page<GetNotificationResponseDto> pageResult = PageableExecutionUtils.getPage(notifications, pageable, countQuery::fetchCount);
        return toPaginationModel(pageResult);
    }

    private OrderSpecifier<?> getOrderSpecifier(String property, String direction) {
        Order order = direction.equalsIgnoreCase("ASC") ? Order.ASC : Order.DESC;

        return switch (property) {
            default -> new OrderSpecifier<>(order, qNotificationEntity.id);
        };
    }

}
