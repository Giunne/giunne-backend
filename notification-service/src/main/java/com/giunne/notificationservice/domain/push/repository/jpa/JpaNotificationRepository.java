package com.giunne.notificationservice.domain.push.repository.jpa;

import com.giunne.notificationservice.domain.push.repository.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByTargetId(Long targetId);

    @Modifying
    @Query("UPDATE NotificationEntity n "
            + "SET n.isRead = true, "
            + "n.updateTime = now() "
            + "WHERE n.targetId = :targetId")
    void readAllNotificationByTargetId(@Param("targetId")Long targetId);

    @Modifying
    @Query("UPDATE NotificationEntity n "
            + "SET n.isRead = true, "
            + "n.updateTime = now() "
            + "WHERE n.id = :id")
    void readNotificationById(@Param("id")Long id);

    int countByTargetIdAndIsReadFalse(Long memberId);
}
