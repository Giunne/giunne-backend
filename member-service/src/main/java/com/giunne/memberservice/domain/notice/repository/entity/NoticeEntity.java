package com.giunne.memberservice.domain.notice.repository.entity;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.memberservice.domain.avatar.repository.entity.AvatarEntity;
import com.giunne.memberservice.domain.notice.domain.Notice;
import com.giunne.memberservice.domain.recreation.repository.entity.RecreationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="notice")
@NoArgsConstructor
@AllArgsConstructor
public class NoticeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_no")
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_no")
    private AvatarEntity writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recreation_no")
    private RecreationEntity recreation;

    public NoticeEntity(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.writer = new AvatarEntity(notice.getWriter());
        this.recreation = new RecreationEntity(notice.getRecreation());
    }

    public Notice toDomain() {
        return Notice.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .writer(this.writer.toAvatar())
                .recreation(this.recreation.toRecreation())
                .createdTime(super.getCreateTime())
                .updatedTime(super.getUpdateTime())
                .build();
    }
}
