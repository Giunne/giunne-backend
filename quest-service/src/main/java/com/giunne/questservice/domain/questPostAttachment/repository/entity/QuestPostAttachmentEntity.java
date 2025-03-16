package com.giunne.questservice.domain.questPostAttachment.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.quest.repository.entity.QuestEntity;
import com.giunne.questservice.domain.questPost.repository.entity.QuestPostEntity;
import com.giunne.questservice.domain.questPostAttachment.domain.type.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quest_attachment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestPostAttachmentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_attachment_no")
    private Long id;

    @Embedded
    private FileName fileName; // 파일명

    @Embedded
    private FileUrl fileUrl; // 파일 URL

    @Embedded
    private FileSize fileSize; // 파일 사이즈

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_no")
    private QuestEntity quest; // 퀘스트

    @Embedded
    private Active isActive = Active.from(true);

    @Embedded
    private IsRepresent isRepresent = IsRepresent.from(true); // 대표유무

    @Embedded
    private SortSeq sortSeq = SortSeq.from(1L); //순서번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_post_no", nullable = true)
    private QuestPostEntity questPost;




}
