package com.giunne.questservice.domain.courseAttachment.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.course.repository.entity.CourseEntity;
import com.giunne.questservice.domain.courseAttachment.domain.CourseAttachment;
import com.giunne.questservice.domain.courseAttachment.domain.type.SortSeq;
import com.giunne.questservice.domain.questAttachment.domain.type.FileName;
import com.giunne.questservice.domain.questAttachment.domain.type.FileSize;
import com.giunne.questservice.domain.questAttachment.domain.type.FileUrl;
import com.giunne.questservice.domain.questAttachment.domain.type.IsRepresent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "course_attachment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseAttachmentEntity extends BaseEntity {

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
    private Active isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_no")
    private CourseEntity course; // 코스

    @Embedded
    private SortSeq sortSeq = SortSeq.from(1L);

    @Embedded
    private IsRepresent isRepresent = IsRepresent.from(true);

    @Builder
    public CourseAttachmentEntity(FileName fileName, FileUrl fileUrl, FileSize fileSize, Active isActive, IsRepresent isRepresent, SortSeq sortSeq) {
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

    public CourseAttachmentEntity(CourseAttachment courseAttachment) {
        this.id = courseAttachment.getId();
        this.sortSeq = courseAttachment.getSortSeq();
        this.isRepresent = courseAttachment.getIsRepresent();
        this.fileSize = courseAttachment.getFileSize();
        this.fileUrl = courseAttachment.getFileUrl();
        this.fileName = courseAttachment.getFileName();
        this.course = new CourseEntity(courseAttachment.getCourse());
    }

    public CourseAttachment toCourseAttachment(){
        return CourseAttachment.builder()
                .id(id)
                .sortSeq(sortSeq)
                .isRepresent(isRepresent)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .fileSize(fileSize)
                .course(course.toCourse())
                .build();
    }

}
