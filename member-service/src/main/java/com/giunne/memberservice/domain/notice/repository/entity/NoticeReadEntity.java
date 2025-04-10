package com.giunne.memberservice.domain.notice.repository.entity;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.memberservice.domain.notice.domain.type.NoticeReadId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="notice_read")
@NoArgsConstructor
@AllArgsConstructor
public class NoticeReadEntity extends BaseEntity {

    @EmbeddedId
    private NoticeReadId id;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    public NoticeReadEntity(Long noticeId, Long playerId, Boolean isRead) {
        this.id = new NoticeReadId(noticeId, playerId);
        this.isRead = isRead;
    }

    public void read() {
        this.isRead = true;
    }

}
