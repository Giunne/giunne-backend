package com.giunne.memberservice.domain.notice.repository.jpa;

import com.giunne.memberservice.domain.notice.domain.type.NoticeReadId;
import com.giunne.memberservice.domain.notice.repository.entity.NoticeEntity;
import com.giunne.memberservice.domain.notice.repository.entity.NoticeReadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaNoticeReadRepository extends JpaRepository<NoticeReadEntity, NoticeReadId> {
    void deleteById_NoticeId(Long noticeId);
    void deleteById_PlayerId(Long playerId);
}
