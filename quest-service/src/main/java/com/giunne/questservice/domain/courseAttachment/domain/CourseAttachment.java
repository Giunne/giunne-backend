package com.giunne.questservice.domain.courseAttachment.domain;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.quest.api.domain.Quest;
import com.giunne.questservice.domain.questAttachment.domain.type.FileName;
import com.giunne.questservice.domain.questAttachment.domain.type.FileSize;
import com.giunne.questservice.domain.questAttachment.domain.type.FileUrl;
import com.giunne.questservice.domain.questAttachment.domain.type.IsRepresent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_attachment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseAttachment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_attachment_no")
    private Long id; // 퀘스트파일 번호

    @Embedded
    private FileName fileName; // 파일명

    @Embedded
    private FileUrl fileUrl; // 파일 URL

    @Embedded
    private FileSize fileSize; // 파일 사이즈

    @Embedded
    private Active isActive = Active.from(true);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_no")
    private Course course; // 코스


    @Embedded
    private IsRepresent isRepresent = IsRepresent.from(true);

    @Builder
    public CourseAttachment(FileName fileName, FileUrl fileUrl, FileSize fileSize, Active isActive, IsRepresent isRepresent) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
        this.isActive = isActive;
        this.isRepresent = isRepresent;
    }

    public void updateProfile(FileUrl fileUrl, FileSize fileSize) {
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
    }

}
