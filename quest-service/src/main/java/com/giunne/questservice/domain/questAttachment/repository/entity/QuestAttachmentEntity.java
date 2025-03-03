package com.giunne.questservice.domain.questAttachment.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.questservice.domain.quest.repository.entity.QuestEntity;
import com.giunne.questservice.domain.questAttachment.domain.QuestAttachment;
import com.giunne.questservice.domain.questAttachment.domain.type.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quest_attachment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestAttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_attachment_no")
    private Long id; // 퀘스트파일 번호

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

    @Builder
    public QuestAttachmentEntity(FileName fileName, FileUrl fileUrl, FileSize fileSize, Active isActive, IsRepresent isRepresent, SortSeq sortSeq) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
        this.isActive = isActive;
        this.isRepresent = isRepresent;
        this.sortSeq = sortSeq;

    }

    public void updateProfile(FileUrl fileUrl, FileSize fileSize) {
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
    }


    public QuestAttachmentEntity(QuestAttachment questAttachment) {
        this.id = questAttachment.getId();
        this.fileName = questAttachment.getFileName();
        this.fileUrl = questAttachment.getFileUrl();
        this.fileSize= questAttachment.getFileSize();
        this.quest = new QuestEntity(questAttachment.getQuest());
        this.isRepresent = questAttachment.getIsRepresent();
        this.sortSeq = questAttachment.getSortSeq();
    }

    public QuestAttachment toQuestAttachment() {
        return QuestAttachment.builder()
                .id(id)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .fileSize(fileSize)
                .quest(quest.toQuest())
                .isRepresent(isRepresent)
                .sortSeq(sortSeq)
                .build();
    }

}
