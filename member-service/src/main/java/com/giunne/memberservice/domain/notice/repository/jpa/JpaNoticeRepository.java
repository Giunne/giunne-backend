package com.giunne.memberservice.domain.notice.repository.jpa;

import com.giunne.memberservice.domain.notice.repository.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaNoticeRepository extends JpaRepository<NoticeEntity, Long> {
    List<NoticeEntity> findAllByRecreation_Id(Long recreationId);
    void deleteByWriter_Id(Long writerId);
}
