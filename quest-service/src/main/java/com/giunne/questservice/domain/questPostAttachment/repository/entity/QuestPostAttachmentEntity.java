package com.giunne.questservice.domain.questPostAttachment.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.questPost.repository.entity.QuestPostEntity;
import com.giunne.questservice.domain.questPostAttachment.domain.QuestPostAttachment;
import com.giunne.questservice.domain.questPostAttachment.domain.type.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quest_post_attachment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestPostAttachmentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_post_attachment_no")
    private Long id;

    @Embedded
    private FileName fileName; // 파일명

    @Embedded
    private FileUrl fileUrl; // 파일 URL

    @Embedded
    private FileSize fileSize; // 파일 사이즈

    @Embedded
    private Active isActive = Active.from(true);

    @Embedded
    private IsRepresent isRepresent = IsRepresent.from(true); // 대표유무

    @Embedded
    private SortSeq sortSeq = SortSeq.from(1L); //순서번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_post_no", nullable = true)
    private QuestPostEntity questPost;

    public QuestPostAttachmentEntity(QuestPostAttachment questPostAttachment) {
        this.id = questPostAttachment.getId();
        this.fileName = questPostAttachment.getFileName();
        this.fileUrl = questPostAttachment.getFileUrl();
        this.fileSize = questPostAttachment.getFileSize();
        this.isRepresent = questPostAttachment.getIsRepresent();
        this.sortSeq = questPostAttachment.getSortSeq();
        this.questPost = new QuestPostEntity(questPostAttachment.getQuestPost());
    }

    public QuestPostAttachment toQuestPostAttachment(){
        return QuestPostAttachment.builder()
                .id(id)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .fileSize(fileSize)
                .isRepresent(isRepresent)
                .sortSeq(sortSeq)
                .questPost(questPost.toQuestPost())
                .build();
    }

}
